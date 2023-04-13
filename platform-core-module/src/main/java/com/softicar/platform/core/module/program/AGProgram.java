package com.softicar.platform.core.module.program;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.AGProgramExecutionGenerated;
import com.softicar.platform.core.module.program.execution.ProgramExecutionInserter;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransactions;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Objects;
import java.util.UUID;

public class AGProgram extends AGProgramGenerated implements IEmfObject<AGProgram> {

	public static final ProgramField PROGRAM = new ProgramField();
	public static final ProgramModuleField MODULE = new ProgramModuleField();
	public static final ProgramAbortRequestedField ABORT_REQUESTED = new ProgramAbortRequestedField();
	public static final ProgramQueuedAtField QUEUED_AT = new ProgramQueuedAtField();
	public static final ProgramQueuedByField QUEUED_BY = new ProgramQueuedByField();
	public static final ProgramCurrentExecutionField CURRENT_EXECUTION = new ProgramCurrentExecutionField();
	public static final ProgramDescriptionField DESCRIPTION = new ProgramDescriptionField();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return Programs.getProgramName(getProgramUuid().getUuid());
	}

	public AGProgram setProgramUuid(UUID programUuid) {

		return setProgramUuid(AGUuid.getOrCreate(programUuid));
	}

	public DayTime getQueuedAt() {

		return getState().getQueuedAt();
	}

	public AGUser getQueuedBy() {

		return getState().getQueuedBy();
	}

	public Boolean isAbortRequested() {

		return getState().isAbortRequested();
	}

	public AGProgramExecution getCurrentExecution() {

		return getState().getCurrentExecution();
	}

	/**
	 * Tests whether the program is queued.
	 *
	 * @return <i>true</i> if it is queued; <i>false</i> otherwise
	 */
	public boolean isQueued() {

		return getState().getQueuedAt() != null;
	}

	/**
	 * Tests whether the program is running.
	 *
	 * @return <i>true</i> if it is running; <i>false</i> otherwise
	 */
	public boolean isRunning() {

		return getState().getCurrentExecution() != null;
	}

	/**
	 * Tests whether the program is queued or running
	 *
	 * @return <i>true</i> if it is queued or running; <i>false</i> otherwise
	 */
	public boolean isQueuedOrRunning() {

		return isQueued() || isRunning();
	}

	public void saveAbortRequested(Boolean value) {

		getState().setAbortRequested(value).save();
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
	public void resetState() {

		getState()//
			.setCurrentExecution(null)
			.setQueuedAt(null)
			.setQueuedBy(null)
			.setAbortRequested(false)
			.save();
	}

	/**
	 * @deprecated user {@link #resetState()} instead
	 */
	@Deprecated
	public void resetAll() {

		resetState();
	}

	/**
	 * Resets {@link AGProgram#CURRENT_EXECUTION}.
	 */
	public void resetCurrentExecution() {

		getState()//
			.setCurrentExecution(null)
			.save();
	}

	public AGProgramState getState() {

		return AGProgramState.TABLE.getOrCreate(getThis());
	}

	public IProgram getProgram() {

		return SourceCodeReferencePoints.getReferencePointOrThrow(getProgramUuid().getUuid(), IProgram.class);
	}

	public void lockProgramState() {

		executeLoadOrInsertProgramState(getThis());
	}

	public static AGProgram loadOrInsert(AGUuid programUuid) {

		Objects.requireNonNull(programUuid);

		return DbTransactions.wrap(AGProgram::executeLoadOrInsertProgram).apply(programUuid);
	}

	private static AGProgram executeLoadOrInsertProgram(AGUuid programUuid) {

		return AGProgram.TABLE//
			.createSelect(SqlSelectLock.FOR_UPDATE)
			.where(AGProgram.PROGRAM_UUID.isEqual(programUuid))
			.getOneAsOptional()
			.orElseGet(() -> executeInsertProgram(programUuid));
	}

	private static AGProgram executeInsertProgram(AGUuid programUuid) {

		return new AGProgram()//
			.setProgramUuid(programUuid)
			.save();
	}

	private static AGProgramState executeLoadOrInsertProgramState(AGProgram program) {

		return AGProgramState.TABLE//
			.createSelect(SqlSelectLock.FOR_UPDATE)
			.where(AGProgramState.PROGRAM.isEqual(program))
			.getOneAsOptional()
			.orElseGet(() -> executeInsertProgramState(program));
	}

	private static AGProgramState executeInsertProgramState(AGProgram program) {

		return AGProgramState.TABLE.getOrCreate(program);
	}
}
