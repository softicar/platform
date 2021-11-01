package com.softicar.platform.core.module.program.abort;

import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.AGProgramGenerated;
import com.softicar.platform.db.core.transaction.DbTransactions;
import java.util.Objects;

/**
 * Facilitates requesting the abort of an {@link AGProgram}.
 *
 * @author Alexander Schmidt
 */
public class ProgramAbortRequester {

	private final AGProgram program;

	/**
	 * Constructs a new {@link ProgramAbortRequester}.
	 *
	 * @param program
	 *            the {@link AGProgram} to abort (never
	 *            <i>null</i>)
	 */
	public ProgramAbortRequester(AGProgram program) {

		this.program = Objects.requireNonNull(program);
	}

	/**
	 * Sets the "abort requested" flag of the {@link AGProgram}.
	 * That is, {@link AGProgramGenerated#ABORT_REQUESTED}.
	 *
	 * @return <i>true</i> if the flag was successfully updated; <i>false</i>
	 *         otherwise
	 */
	public boolean requestAbort() {

		return DbTransactions.wrap(this::requestAbortIfPossible).get();
	}

	private boolean requestAbortIfPossible() {

		if (program.reloadForUpdate() && program.isQueuedOrRunning()) {
			program//
				.setAbortRequested(true)
				.save();
			return true;
		} else {
			return false;
		}
	}
}
