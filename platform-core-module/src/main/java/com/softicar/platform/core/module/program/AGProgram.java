package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.AGProgramExecutionGenerated;
import com.softicar.platform.core.module.program.execution.ProgramExecutionInserter;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransactions;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Objects;
import java.util.UUID;

public class AGProgram extends AGProgramGenerated implements IEmfObject<AGProgram> {

	public static final AbortRequestedField ABORT_REQUESTED = new AbortRequestedField();
	public static final QueuedAtField QUEUED_AT = new QueuedAtField();
	public static final QueuedByField QUEUED_BY = new QueuedByField();
	public static final CurrentExecutionField CURRENT_EXECUTION = new CurrentExecutionField();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return Programs.getProgramName(getProgramUuid().getUuid());
	}

	public AGProgram setProgramUuid(UUID programUuid) {

		return setProgramUuid(AGUuid.getOrCreate(programUuid));
	}

	public DayTime getQueuedAt() {

		return getOrCreateProgramState().getQueuedAt();
	}

	public void saveQueuedAt(DayTime value) {

		getOrCreateProgramState().setQueuedAt(value).save();
	}

	public AGUser getQueuedBy() {

		return getOrCreateProgramState().getQueuedBy();
	}

	public void saveQueuedBy(AGUser value) {

		getOrCreateProgramState().setQueuedBy(value).save();
	}

	public Boolean isAbortRequested() {

		return getOrCreateProgramState().isAbortRequested();
	}

	public void saveAbortRequested(Boolean value) {

		getOrCreateProgramState().setAbortRequested(value).save();
	}

	public AGProgramExecution getCurrentExecution() {

		return getOrCreateProgramState().getCurrentExecution();
	}

	public void saveCurrentExecution(AGProgramExecution value) {

		getOrCreateProgramState().setCurrentExecution(value).save();
	}

	/**
	 * Tests whether the program is queued.
	 *
	 * @return <i>true</i> if it is queued; <i>false</i> otherwise
	 */
	public boolean isQueued() {

		return getOrCreateProgramState().getQueuedAt() != null;
	}

	/**
	 * Tests whether the program is running.
	 *
	 * @return <i>true</i> if it is running; <i>false</i> otherwise
	 */
	public boolean isRunning() {

		return getOrCreateProgramState().getCurrentExecution() != null;
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
	 * The {@link AGProgramState#QUEUED_AT} field will be updated to
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

		getOrCreateProgramState()//
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

		getOrCreateProgramState()//
			.setCurrentExecution(null)
			.save();
	}

	public AGProgramState getOrCreateProgramState() {

		return AGProgramState.TABLE.getOrCreate(getThis());
	}

	public boolean reloadProgramState() {

		return getOrCreateProgramState().reload();
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

		AGProgram program = new AGProgram()//
			.setProgramUuid(programUuid)
			.save();
		program.getOrCreateProgramState().save();
		return program;
	}
}
