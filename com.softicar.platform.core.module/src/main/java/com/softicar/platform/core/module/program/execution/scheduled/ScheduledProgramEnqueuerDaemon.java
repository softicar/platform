package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.core.module.daemon.IDaemon;

class ScheduledProgramEnqueuerDaemon implements IDaemon {

	private final ScheduledProgramEnqueuerDaemonTimer timer;

	public ScheduledProgramEnqueuerDaemon() {

		this.timer = new ScheduledProgramEnqueuerDaemonTimer();
	}

	@Override
	public void runIteration() {

		timer.sleepUntilNextMinute();
		enqueueExecutions();
	}

	private void enqueueExecutions() {

		AGScheduledProgramExecution.TABLE//
			.createSelect()
			.where(AGScheduledProgramExecution.ACTIVE)
			.forEach(it -> it.enqueueExecutionIfScheduleMatches(timer.getCurrentMinute()));
	}
}
