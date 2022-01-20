package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.core.transaction.DbTransactions;
import java.util.Objects;

/**
 * Internal class used by the method {@link AGProgram#insertCurrentExecution()}.
 *
 * @author Oliver Richers
 */
public class ProgramExecutionInserter {

	private final AGProgram program;

	public ProgramExecutionInserter(AGProgram program) {

		this.program = Objects.requireNonNull(program);
	}

	public AGProgramExecution insert() {

		return DbTransactions.wrap(this::insertAndUpdateCurrentExecution).get();
	}

	private AGProgramExecution insertAndUpdateCurrentExecution() {

		program.reloadProgramStateForUpdate();
		program//
			.getOrCreateProgramState()
			.setCurrentExecution(insertExecution(program.getQueuedBy()))
			.setQueuedBy(null)
			.setQueuedAt(null)
			.save();
		return program.getCurrentExecution();
	}

	private AGProgramExecution insertExecution(AGUser queuedBy) {

		return new AGProgramExecution()//
			.setProgramUuid(program.getProgramUuid())
			.setQueuedBy(queuedBy)
			.setStartedAt(null)
			.setTerminatedAt(null)
			.save();
	}
}
