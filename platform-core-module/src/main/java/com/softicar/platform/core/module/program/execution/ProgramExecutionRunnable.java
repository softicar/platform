package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.core.logging.ILogOutput;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.logging.LogBuffer;
import com.softicar.platform.common.core.logging.LogOutputScope;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.event.SystemEventBuilder;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.program.ProgramStarter;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.table.row.DbTableRowProxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ProgramExecutionRunnable implements Runnable {

	private final Supplier<AGProgramExecution> executionProxy;
	private final ILogOutput logOutput;
	private boolean failed;

	public ProgramExecutionRunnable(AGProgramExecution execution) {

		this.executionProxy = new DbTableRowProxy<>(execution);
		this.logOutput = new TimestampedLogOutput(new LogBuffer());
		this.failed = false;
	}

	@Override
	public void run() {

		ProgramExecutionRunnableRegistry.getInstance().register(getExecution(), this);
		updateStartedAt();
		try {
			try {
				executeProgram();
			} finally {
				updateOutputAndTerminatedAt();
				if (failed) {
					new SystemEventBuilder(AGSystemEventSeverityEnum.ERROR, CoreI18n.PROGRAM_EXECUTION_FAILED.toString())//
						.addProperty("program", getExecution().getProgram().toDisplay())
						.save();
				}
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			DbConnections.closeAll();
			ProgramExecutionRunnableRegistry.getInstance().unregister(getExecution());
		}
	}

	public AGProgramExecution getExecution() {

		return executionProxy.get();
	}

	public UUID getProgramUuid() {

		return getExecution()//
			.getProgramUuid()
			.getUuid();
	}

	public String getLogs() {

		return logOutput.toString();
	}

	private AGProgramExecution updateStartedAt() {

		return getExecution()//
			.setStartedAt(DayTime.now())
			.save();
	}

	private void executeProgram() {

		try (var scope = new LogOutputScope(logOutput)) {
			Log.finfo("Program started.");
			new ProgramStarter(getProgramUuid()).start();
			Log.finfo("Program finished.");
		} catch (Throwable throwable) {
			this.failed = true;
			// TODO this seems to duplicate some log lines we already have
			logStackTrace(throwable);
		}
	}

	private void logStackTrace(Throwable throwable) {

		logOutput.logLine(StackTraceFormatting.getStackTraceAsString(throwable));
	}

	private void updateOutputAndTerminatedAt() {

		try (var transaction = new DbTransaction()) {
			var execution = getExecution();
			execution.reloadForUpdate();
			execution//
				.setTerminatedAt(DayTime.now())
				.setFailed(failed)
				.setOutput(logOutput.toString())
				.save();
			transaction.commit();
		}
	}

	private static class TimestampedLogOutput implements ILogOutput {

		private final ILogOutput originalOutput;

		public TimestampedLogOutput(ILogOutput originalOutput) {

			this.originalOutput = Objects.requireNonNull(originalOutput);
		}

		@Override
		public void logLine(String line) {

			line = Arrays//
				.asList(line.split("\n"))
				.stream()
				.map(subLine -> "[%s] %s".formatted(DayTime.now(), subLine))
				.collect(Collectors.joining("\n"));
			originalOutput.logLine(line);
		}

		@Override
		public String toString() {

			return originalOutput.toString();
		}
	}
}
