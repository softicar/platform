package com.softicar.platform.core.module.program.execution.cleanup;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.event.SystemEventBuilder;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.db.core.transaction.DbTransaction;

public class ProgramExecutionsCleaner {

	public void cleanupOrphanedRecords() {

		try (var transaction = new DbTransaction()) {
			AGProgramExecution.TABLE//
				.createSelect()
				.where(AGProgramExecution.TERMINATED_AT.isNull())
				.joinLeftReverse(AGProgramState.CURRENT_EXECUTION)
				.where(AGProgramState.TABLE.isNull())
				.forEach(this::terminateExecutionAndCreateEvent);
			transaction.commit();
		}
	}

	private void terminateExecutionAndCreateEvent(AGProgramExecution execution) {

		execution//
			.setFailed(true)
			.setTerminatedAt(DayTime.now())
			.save();
		new SystemEventBuilder(AGSystemEventSeverityEnum.ERROR, CoreI18n.PROGRAM_EXECUTION_FAILED.toString())//
			.addProperty("program", Programs.getProgramName(execution.getProgramUuid().getUuid()).toString())
			.save();
	}
}
