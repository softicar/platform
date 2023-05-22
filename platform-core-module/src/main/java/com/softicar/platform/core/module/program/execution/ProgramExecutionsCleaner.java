package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.event.SystemEventBuilder;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.db.core.transaction.DbTransaction;

public class ProgramExecutionsCleaner {

	public void cleanupOrphanedExecutions() {

		try (var transaction = new DbTransaction()) {
			resetAllProgramStates();
			terminateAllProgramExecutions();
			transaction.commit();
		}
	}

	private void resetAllProgramStates() {

		AGProgram.TABLE//
			.loadAll()
			.forEach(AGProgram::resetState);
	}

	private void terminateAllProgramExecutions() {

		AGProgramExecution.TABLE//
			.createSelect()
			.where(AGProgramExecution.TERMINATED_AT.isNull())
			.joinLeftReverse(AGProgramState.CURRENT_EXECUTION)
			.where(AGProgramState.PROGRAM.isNull())
			.forEach(this::terminateExecutionAndCreateEvent);
	}

	private void terminateExecutionAndCreateEvent(AGProgramExecution execution) {

		execution//
			.setFailed(true)
			.setTerminatedAt(DayTime.now())
			.save();
		new SystemEventBuilder(AGSystemEventSeverityEnum.WARNING, CoreI18n.ORPHANED_PROGRAM_EXECUTION_CLEANUP.toString())//
			.addProperty("program", execution.getProgram().toDisplay())
			.save();
	}
}
