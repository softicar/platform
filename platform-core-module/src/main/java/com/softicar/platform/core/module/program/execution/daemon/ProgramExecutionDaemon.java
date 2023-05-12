package com.softicar.platform.core.module.program.execution.daemon;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.thread.IRunnableThread;
import com.softicar.platform.common.core.thread.runner.ILimitedThreadRunner;
import com.softicar.platform.common.core.thread.runner.LimitedThreadRunner;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.daemon.DaemonProperties;
import com.softicar.platform.core.module.daemon.IDaemon;
import com.softicar.platform.core.module.event.SystemEventBuilder;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.MissingProgramsInserter;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.ProgramExecutionRunnable;
import com.softicar.platform.core.module.program.execution.ProgramExecutionsCleaner;
import com.softicar.platform.core.module.program.execution.scheduled.AGScheduledProgramExecution;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransactions;
import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

class ProgramExecutionDaemon implements IDaemon {

	private static final Duration SLEEP_DURATION = Duration.ofMillis(DaemonProperties.PROGRAM_EXECUTION_DAEMON_SLEEP_MILLIS.getValue());
	private final ProgramExecutionRegisteredThreadRunner registeredThreadRunner;

	public ProgramExecutionDaemon() {

		this(new LimitedThreadRunner<>(DaemonProperties.PROGRAM_EXECUTION_THREAD_COUNT.getValue()));
	}

	public ProgramExecutionDaemon(ILimitedThreadRunner<ProgramExecutionRunnable> threadRunner) {

		this.registeredThreadRunner = new ProgramExecutionRegisteredThreadRunner(threadRunner);
	}

	@Override
	public void setup() {

		new MissingProgramsInserter().insertMissingPrograms();
		new ProgramExecutionsCleaner().cleanupOrphanedExecutions();
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

		if (program.isAbortRequested()) {
			// when terminating a program, the current execution may not be locked
			terminateProgram(program);
		} else {
			var currentExecution = program.getCurrentExecution();
			currentExecution.reloadForUpdate();
			if (currentExecution.isTerminated()) {
				program.resetCurrentExecution();
			} else {
				handleMaximumRuntime(program, currentExecution);
			}
		}
	}

	private void handleNonRunningProgram(AGProgram program) {

		if (!program.isRunning()) {
			if (program.isAbortRequested()) {
				program.resetState();
			} else if (program.isQueued() && registeredThreadRunner.hasAvailableSlots() && !AGMaintenanceWindow.isMaintenanceInProgress()) {
				addRunnable(program);
			}
		}
	}

	private void addRunnable(AGProgram program) {

		registeredThreadRunner.addRunnable(new ProgramExecutionRunnable(program.insertCurrentExecution()));
	}

	private void terminateProgram(AGProgram program) {

		if (terminate(getRunningRunnableThreads(program))) {
			program.resetState();
		}
	}

	private boolean terminate(Collection<IRunnableThread<ProgramExecutionRunnable>> runnableThreads) {

		var allKilled = true;
		for (var runnableThread: runnableThreads) {
			var killed = runnableThread.kill();
			if (!killed) {
				allKilled = false;
				Log.ferror("Timeout while trying to terminate a thread for program: %s", getProgramName(runnableThread));
			}
		}
		return allKilled;
	}

	private void handleMaximumRuntime(AGProgram program, AGProgramExecution currentExecution) {

		var scheduledExecution = AGScheduledProgramExecution.loadByProgramUuid(program.getProgramUuid());
		if (scheduledExecution != null && scheduledExecution.isMaximumRuntimeExceeded(currentExecution)) {
			if (scheduledExecution.isAutomaticAbort()) {
				program.saveAbortRequested(true);
			}
			if (!currentExecution.isMaximumRuntimeExceeded()) {
				createRuntimeExceededEvent(program, currentExecution);
				currentExecution.setMaximumRuntimeExceeded(true).save();
			}
		}
	}

	private void createRuntimeExceededEvent(AGProgram program, AGProgramExecution currentExecution) {

		new SystemEventBuilder(AGSystemEventSeverityEnum.ERROR, CoreI18n.PROGRAM_EXECUTION_EXCEEDED_MAXIMUM_RUNTIME.toString())//
			.addProperty("program", program.toDisplay())
			.addProperty("start", currentExecution.getStartedAt().toString())
			.save();
	}

	private void resetQueueEntries(Collection<IRunnableThread<ProgramExecutionRunnable>> runnableThreads) {

		runnableThreads//
			.stream()
			.map(IRunnableThread::getRunnable)
			.map(ProgramExecutionRunnable::getProgramUuid)
			.map(AGUuid::getOrCreate)
			.map(AGProgram::loadByProgramUuid)
			.filter(Objects::nonNull)
			.forEach(AGProgram::resetState);
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
