package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.core.logging.LogBuffer;
import com.softicar.platform.common.core.logging.LogOutputScope;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.event.SystemEventBuilder;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.program.ProgramStarter;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.runtime.table.row.DbTableRowProxy;
import java.util.UUID;
import java.util.function.Supplier;

public class ProgramExecutionRunnable implements Runnable {

	private final Supplier<AGProgramExecution> executionProxy;
	private final LogBuffer logBuffer;
	private boolean failed;

	public ProgramExecutionRunnable(AGProgramExecution execution) {

		this.executionProxy = new DbTableRowProxy<>(execution);
		this.logBuffer = new LogBuffer();
		this.failed = false;
	}

	@Override
	public void run() {

		try {
			updateStartedAt();

			try {
				executeProgram();
			} finally {
				updateOutputAndTerminatedAt();
				if (failed) {
					new SystemEventBuilder(AGSystemEventSeverityEnum.WARNING, CoreI18n.PROGRAM_EXECUTION_FAILED.toString())//
						.addProperty("program", Programs.getProgramName(getProgramUuid()).toString())
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

		try (LogOutputScope scope = new LogOutputScope(logBuffer)) {
			new ProgramStarter(getProgramUuid()).start();
		} catch (Throwable throwable) {
			this.failed = true;
			logStackTrace(throwable);
		}
	}

	private void logStackTrace(Throwable throwable) {

		logBuffer.logLine(StackTraceFormatting.getStackTraceAsString(throwable));
	}

	private AGProgramExecution updateOutputAndTerminatedAt() {

		return getExecution()//
			.setTerminatedAt(DayTime.now())
			.setFailed(failed)
			.setOutput(logBuffer.toString())
			.save();
	}
}
