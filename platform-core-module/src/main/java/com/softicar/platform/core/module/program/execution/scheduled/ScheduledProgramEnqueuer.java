package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransactions;

class ScheduledProgramEnqueuer {

	private final AGScheduledProgramExecution scheduledProgramExecution;
	private final DayTime currentMinute;

	public ScheduledProgramEnqueuer(AGScheduledProgramExecution scheduledProgramExecution, DayTime dayTime) {

		this.scheduledProgramExecution = scheduledProgramExecution;
		this.currentMinute = dayTime.truncateSeconds();
	}

	public void enqueueExecution() {

		if (scheduledProgramExecution.isScheduleMatching(currentMinute)) {
			DbTransactions.wrap(this::enqueueExecutionIfAppropriate).accept(scheduledProgramExecution.getProgramUuid());
		}
	}

	private void enqueueExecutionIfAppropriate(AGUuid programUuid) {

		AGProgram program = loadOrInsertProgram(programUuid);
		if (program.reloadProgramStateForUpdate() && isNotQueuedAndNotRunning(program)) {
			updateQueuedAtAndQueuedBy(program);
		}
	}

	private AGProgram loadOrInsertProgram(AGUuid programUuid) {

		return AGProgram.loadOrInsert(programUuid);
	}

	private boolean isNotQueuedAndNotRunning(AGProgram program) {

		return !program.isQueued() && !hasRecentExecutions(program);
	}

	private boolean hasRecentExecutions(AGProgram program) {

		return !AGProgramExecution.getRecentExecutions(program, currentMinute).isEmpty();
	}

	private void updateQueuedAtAndQueuedBy(AGProgram program) {

		program//
			.getOrCreateProgramState()
			.setQueuedAt(currentMinute)
			.setQueuedBy(AGCoreModuleInstance.getInstance().getSystemUser())
			.save();
	}
}
