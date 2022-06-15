package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.thread.IRunnableThread;
import com.softicar.platform.common.core.thread.runner.ILimitedThreadRunner;
import com.softicar.platform.common.core.thread.runner.LimitedThreadRunner;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.daemon.DaemonProperties;
import com.softicar.platform.core.module.daemon.IDaemon;
import com.softicar.platform.core.module.event.SystemEventBuilder;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.ProgramExecutionRunnable;
import com.softicar.platform.core.module.program.execution.scheduled.AGScheduledProgramExecution;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransactions;
import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

class QueuedProgramExecutionDaemon implements IDaemon {

	private static final Duration SLEEP_DURATION = Duration.ofMillis(DaemonProperties.QUEUED_PROGRAM_EXECUTION_DAEMON_SLEEP_MILLIS.getValue());
	private final QueuedProgramExecutionRegisteredThreadRunner registeredThreadRunner;

	public QueuedProgramExecutionDaemon() {

		this(new LimitedThreadRunner<>(DaemonProperties.QUEUED_PROGRAM_EXECUTION_THREAD_COUNT.getValue()));
	}

	public QueuedProgramExecutionDaemon(ILimitedThreadRunner<ProgramExecutionRunnable> threadRunner) {

		this.registeredThreadRunner = new QueuedProgramExecutionRegisteredThreadRunner(threadRunner);
	}

	@Override
	public void runIteration() {

		loadAllRelevantPrograms().forEach(DbTransactions.wrap(this::handleProgram));
		registeredThreadRunner.startThreads();
		Sleep.sleep(SLEEP_DURATION);
	}

	@Override
	public void cleanup() {

		var runnableThreads = registeredThreadRunner.getAllRunning();
		terminate(runnableThreads);
		resetQueueEntries(runnableThreads);
	}

	private void handleProgram(AGProgram program) {

		program.lockProgramState();

		if (program.isRunning()) {
			handleRunningProgram(program);
		} else {
			handleNonRunningProgram(program);
		}
	}

	private void handleRunningProgram(AGProgram program) {

		AGProgramExecution currentExecution = program.getCurrentExecution();
		currentExecution.reload();

		if (currentExecution.isTerminated()) {
			program.resetCurrentExecution();
		}

		if (program.isAbortRequested()) {
			terminateProgram(program);
		}

		if (!currentExecution.isTerminated() && !program.isAbortRequested()) {
			checkMaximumRuntime(program, currentExecution);
		}
	}

	private void handleNonRunningProgram(AGProgram program) {

		if (!program.isRunning()) {
			if (program.isAbortRequested()) {
				program.resetAll();
			} else if (program.isQueued() && registeredThreadRunner.hasAvailableSlots()) {
				addRunnable(program);
			}
		}
	}

	private void addRunnable(AGProgram program) {

		registeredThreadRunner.addRunnable(new ProgramExecutionRunnable(program.insertCurrentExecution()));
	}

	private void terminateProgram(AGProgram program) {

		terminate(getRunningRunnableThreads(program));
		program.resetAll();
	}

	private void terminate(Collection<IRunnableThread<ProgramExecutionRunnable>> runnableThreads) {

		for (IRunnableThread<ProgramExecutionRunnable> runnableThread: runnableThreads) {
			boolean killed = runnableThread.kill();
			if (!killed) {
				Log.ferror("Timeout while trying to terminate a thread for program: %s", getProgramName(runnableThread));
			}
		}
	}

	private void checkMaximumRuntime(AGProgram program, AGProgramExecution currentExecution) {

		var scheduledExecution = AGScheduledProgramExecution.getByAGUuid(program.getProgramUuid());
		if (scheduledExecution != null) {
			var maximumRuntimeInSeconds = scheduledExecution.getMaximumRuntimeInSeconds();
			if (maximumRuntimeInSeconds.isPresent()) {
				boolean maximumRuntimeExceeded = DayTime.now().isAfter(currentExecution.getStartedAt().plusSeconds(maximumRuntimeInSeconds.get()));
				if (maximumRuntimeExceeded && !currentExecution.isExceededMaximumRuntime()) {
					new SystemEventBuilder(AGSystemEventSeverityEnum.WARNING, CoreI18n.PROGRAM_EXECUTION_EXCEEDED_MAXIMUM_RUNTIME.toString())//
						.addProperty("program", program.toDisplay().toString())
						.addProperty("start", currentExecution.getStartedAt().toGermanString())
						.addProperty("maximumRuntimeSeconds", "" + maximumRuntimeInSeconds)
						.save();
					if (scheduledExecution.isAutoKill()) {
						terminateProgram(program);
					}
					currentExecution.reload();
					currentExecution.setExceededMaximumRuntime(true).save();
				}
			}
		}
	}

	private void resetQueueEntries(Collection<IRunnableThread<ProgramExecutionRunnable>> runnableThreads) {

		runnableThreads//
			.stream()
			.map(IRunnableThread::getRunnable)
			.map(ProgramExecutionRunnable::getProgramUuid)
			.map(AGUuid::getOrCreate)
			.map(AGProgram::loadByProgramUuid)
			.filter(Objects::nonNull)
			.forEach(AGProgram::resetAll);
	}

	private Collection<IRunnableThread<ProgramExecutionRunnable>> getRunningRunnableThreads(AGProgram program) {

		return registeredThreadRunner.getAllRunning(program.getProgramUuid().getUuid());
	}

	private IDisplayString getProgramName(IRunnableThread<ProgramExecutionRunnable> runnableThread) {

		UUID programUuid = runnableThread.getRunnable().getProgramUuid();
		return Programs.getProgramName(programUuid);
	}

	/**
	 * Loads all {@link AGProgram} rows that have a non-default value in at
	 * least one of their fields.
	 * <p>
	 * The returned {@link Collection} is sorted by "queued-at" time, in
	 * ascending order.
	 *
	 * @return all relevant {@link AGProgram} rows (never <i>null</i>)
	 */
	private Collection<AGProgram> loadAllRelevantPrograms() {

		return AGProgramState.TABLE
			.createSelect()
			.where(AGProgramState.ABORT_REQUESTED)
			.orWhere(AGProgramState.QUEUED_AT.isNotNull())
			.orWhere(AGProgramState.CURRENT_EXECUTION.isNotNull())
			.orderBy(AGProgramState.QUEUED_AT)
			.orderBy(AGProgramState.PROGRAM)
			.stream()
			.map(AGProgramState::getProgram)
			.collect(Collectors.toList());
	}
}
