package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.AGProgramExecutionGenerated;
import com.softicar.platform.core.module.program.execution.ProgramExecutionInserter;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransactions;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Objects;
import java.util.UUID;

public class AGProgram extends AGProgramGenerated implements IEmfObject<AGProgram> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return Programs.getProgramName(getProgramUuid().getUuid());
	}

	public AGProgram setProgramUuid(UUID programUuid) {

		return setProgramUuid(AGUuid.getOrCreate(programUuid));
	}

	/**
	 * Tests whether the program is queued.
	 *
	 * @return <i>true</i> if it is queued; <i>false</i> otherwise
	 */
	public boolean isQueued() {

		return getQueuedAt() != null;
	}

	/**
	 * Tests whether the program is running.
	 *
	 * @return <i>true</i> if it is running; <i>false</i> otherwise
	 */
	public boolean isRunning() {

		return getCurrentExecution() != null;
	}

	/**
	 * Tests whether the program is queued or running
	 *
	 * @return <i>true</i> if it is queued or running; <i>false</i> otherwise
	 */
	public boolean isQueuedOrRunning() {

		return isQueued() || isRunning();
	}

	/**
	 * Inserts a new {@link AGProgramExecution} as current execution of this
	 * {@link AGProgram}.
	 * <p>
	 * The fields {@link AGProgramExecutionGenerated#STARTED_AT} and
	 * {@link AGProgramExecutionGenerated#TERMINATED_AT} will be inserted with
	 * <i>null</i> values.
	 * <p>
	 * The {@link AGProgramGenerated#QUEUED_AT} field will be updated to
	 * <i>null</i>.
	 *
	 * @return the current {@link AGProgramExecution}
	 */
	public AGProgramExecution insertCurrentExecution() {

		return new ProgramExecutionInserter(this).insert();
	}

	/**
	 * Resets {@link AGProgram#CURRENT_EXECUTION}, {@link AGProgram#QUEUED_AT},
	 * {@link AGProgram#QUEUED_BY} and {@link AGProgram#ABORT_REQUESTED} to
	 * their respective default values.
	 */
	public void resetAll() {

		getThis()//
			.setCurrentExecution(null)
			.setQueuedAt(null)
			.setQueuedBy(null)
			.setAbortRequested(false)
			.save();
	}

	/**
	 * Resets {@link AGProgram#CURRENT_EXECUTION}.
	 */
	public void resetCurrentExecution() {

		getThis()//
			.setCurrentExecution(null)
			.save();
	}

	public static AGProgram loadOrInsert(AGUuid programUuid) {

		Objects.requireNonNull(programUuid);

		return DbTransactions.wrap(AGProgram::executeLoadOrInsert).apply(programUuid);
	}

	private static AGProgram executeLoadOrInsert(AGUuid programUuid) {

		return AGProgram.TABLE//
			.createSelect(SqlSelectLock.FOR_UPDATE)
			.where(AGProgram.PROGRAM_UUID.isEqual(programUuid))
			.getOneAsOptional()
			.orElseGet(() -> executeInsert(programUuid));
	}

	private static AGProgram executeInsert(AGUuid programUuid) {

		return new AGProgram()//
			.setProgramUuid(programUuid)
			.setCurrentExecution(null)
			.setQueuedAt(null)
			.setQueuedBy(null)
			.save();
	}
}
