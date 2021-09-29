package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.date.DayTime;
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
		if (program.reloadForUpdate() && isNotQueuedAndNotRunning(program)) {
			updateQueuedAt(program);
		}
	}

	private AGProgram loadOrInsertProgram(AGUuid programUuid) {

		return AGProgram.loadOrInsert(programUuid);
	}

	private boolean isNotQueuedAndNotRunning(AGProgram program) {

		return !program.isQueued() && !hasRecentExecutions();
	}

	private boolean hasRecentExecutions() {

		return !AGProgramExecution.getRecentExecutions(currentMinute).isEmpty();
	}

	private AGProgram updateQueuedAt(AGProgram program) {

		return program//
			.setQueuedAt(currentMinute)
			.save();
	}
}
