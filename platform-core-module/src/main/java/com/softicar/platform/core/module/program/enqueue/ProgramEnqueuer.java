package com.softicar.platform.core.module.program.enqueue;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;

public class ProgramEnqueuer {

	private final AGProgram program;

	public ProgramEnqueuer(AGProgram program) {

		this.program = program;
	}

	public void enqueueProgram() {

		try (var transaction = new DbTransaction()) {
			program.lockProgramState();
			if (!program.isQueued()) {
				program//
					.getState()
					.setQueuedAt(DayTime.now().truncateSeconds())
					.setQueuedBy(CurrentUser.get())
					.save();
			}
			transaction.commit();
		}
	}
}
