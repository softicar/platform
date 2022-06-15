package com.softicar.platform.core.module.program;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransactions;

/**
 * Enqueues the given {@link IProgram} into {@link AGProgram}.
 *
 * @author Oliver Richers
 */
class ProgramEnqueuer<P extends IProgram> {

	private final Class<P> programClass;

	public ProgramEnqueuer(Class<P> programClass) {

		this.programClass = programClass;
	}

	public void enqueue() {

		DbTransactions.wrap(this::enqueueExecution).accept(getProgramUuid());
	}

	private AGUuid getProgramUuid() {

		return AGUuid.getOrCreate(programClass);
	}

	private void enqueueExecution(AGUuid programUuid) {

		AGProgram program = AGProgram.loadOrInsert(programUuid);
		program.lockProgramState();
		program//
			.getState()
			.setQueuedAt(DayTime.now())
			.setQueuedBy(AGCoreModuleInstance.getInstance().getSystemUser())
			.save();
	}
}
