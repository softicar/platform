package com.softicar.platform.core.module.program.unqueue;

import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.AGProgramState;
import com.softicar.platform.db.core.transaction.DbTransactions;
import java.util.Objects;

/**
 * Facilitates removing a previously-queued {@link AGProgram} from the queue.
 *
 * @author Alexander Schmidt
 */
public class ProgramUnqueuer {

	private final AGProgram program;

	/**
	 * Constructs a new {@link ProgramUnqueuer}.
	 *
	 * @param program
	 *            the {@link AGProgram} to remove from the queue (never
	 *            <i>null</i>)
	 */
	public ProgramUnqueuer(AGProgram program) {

		this.program = Objects.requireNonNull(program);
	}

	/**
	 * Removes the {@link AGProgram} from the queue. That is, resets
	 * {@link AGProgramState#QUEUED_AT} to its default value.
	 *
	 * @return <i>true</i> if the execution was actually removed from the queue;
	 *         <i>false</i> otherwise
	 */
	public boolean removeFromQueue() {

		return DbTransactions.wrap(this::removeFromQueueIfPossible).get();
	}

	private boolean removeFromQueueIfPossible() {

		if (program.reloadForUpdate() && program.reloadProgramState() && program.isQueued()) {
			program//
				.getOrCreateProgramState()
				.setQueuedAt(null)
				.setQueuedBy(null)
				.save();
			return true;
		} else {
			return false;
		}
	}
}
