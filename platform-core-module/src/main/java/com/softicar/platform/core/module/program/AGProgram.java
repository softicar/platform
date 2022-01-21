package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.AGProgramExecutionGenerated;
import com.softicar.platform.core.module.program.execution.ProgramExecutionInserter;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransactions;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Objects;
import java.util.UUID;

public class AGProgram extends AGProgramGenerated implements IEmfObject<AGProgram> {

	public static final ProgramAbortRequestedField ABORT_REQUESTED = new ProgramAbortRequestedField();
	public static final ProgramQueuedAtField QUEUED_AT = new ProgramQueuedAtField();
	public static final ProgramQueuedByField QUEUED_BY = new ProgramQueuedByField();
	public static final ProgramCurrentExecutionField CURRENT_EXECUTION = new ProgramCurrentExecutionField();

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

	public AGUser getQueuedBy() {

		return getOrCreateProgramState().getQueuedBy();
	}

	public Boolean isAbortRequested() {

		return getOrCreateProgramState().isAbortRequested();
	}

	public AGProgramExecution getCurrentExecution() {

		return getOrCreateProgramState().getCurrentExecution();
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

	public boolean reloadProgramStateForUpdate() {

		return getOrCreateProgramState().reloadForUpdate();
	}

	public static AGProgram loadOrInsert(AGUuid programUuid) {

		Objects.requireNonNull(programUuid);

		return DbTransactions.wrap(AGProgram::executeLoadOrInsert).apply(programUuid);
	}

	private static AGProgram executeLoadOrInsert(AGUuid programUuid) {

		AGProgram program = AGProgram.loadByProgramUuid(programUuid);
		if (program == null) {
			return executeInsert(programUuid);
		} else {
			return program;
		}
	}

	private static AGProgram executeInsert(AGUuid programUuid) {

		AGProgram program = new AGProgram()//
			.setProgramUuid(programUuid)
			.save();
		program.getOrCreateProgramState().save();
		return program;
	}
}
