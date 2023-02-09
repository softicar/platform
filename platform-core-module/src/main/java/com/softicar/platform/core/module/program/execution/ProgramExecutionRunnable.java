package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.core.logging.ILogOutput;
import com.softicar.platform.common.core.logging.LogBuffer;
import com.softicar.platform.common.core.logging.LogFileOutput;
import com.softicar.platform.common.core.logging.LogOutputScope;
import com.softicar.platform.common.core.logging.MultiLogOutput;
import com.softicar.platform.common.core.properties.SofticarHome;
import com.softicar.platform.common.core.properties.SystemPropertyEnum;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.event.SystemEventBuilder;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.program.ProgramStarter;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.table.row.DbTableRowProxy;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class ProgramExecutionRunnable implements Runnable {

	private final Supplier<AGProgramExecution> executionProxy;
	private final LogBuffer logBuffer;
	private final Path logFilePath;
	private boolean failed;

	public ProgramExecutionRunnable(AGProgramExecution execution) {

		Objects.requireNonNull(execution);
		this.executionProxy = new DbTableRowProxy<>(execution);
		this.logBuffer = new LogBuffer();
		this.logFilePath = getLogFilePath(execution);
		this.failed = false;
	}

	@Override
	public void run() {

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

	private AGProgramExecution updateStartedAt() {

		return getExecution()//
			.setStartedAt(DayTime.now())
			.save();
	}

	private void executeProgram() {

		try (var logFileOutput = new LogFileOutput(logFilePath)) {
			var logOutputDistributor = new MultiLogOutput(logFileOutput, logBuffer);
			try (var scope = new LogOutputScope(logOutputDistributor)) {
				new ProgramStarter(getProgramUuid()).start();
			} catch (Throwable throwable) {
				this.failed = true;
				logStackTrace(logOutputDistributor, throwable);
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void logStackTrace(ILogOutput logOutput, Throwable throwable) {

		logOutput.logLine(StackTraceFormatting.getStackTraceAsString(throwable));
	}

	private void updateOutputAndTerminatedAt() {

		try (var transaction = new DbTransaction()) {
			var execution = getExecution();
			execution.reloadForUpdate();
			execution//
				.setTerminatedAt(DayTime.now())
				.setFailed(failed)
				.setOutput(logBuffer.toString())
				.save();
			transaction.commit();
		}
		logFilePath.toFile().delete();
	}

	private Path getLogFilePath(AGProgramExecution execution) {

		String logFilename = "softicar-program-execution-%s.log".formatted(execution.getId());
		return getLogDirectory().resolve(logFilename);
	}

	private Path getLogDirectory() {

		if (AGCoreModuleInstance.getInstance().isTestSystem()) {
			return Path.of(SystemPropertyEnum.JAVA_IO_TMPDIR.get());
		} else {
			return SofticarHome.getLogPath();
		}
	}
}
