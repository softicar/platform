package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGProgramState for
 * database table <i>Core.ProgramState</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGProgramState extends AbstractDbRecord<AGProgramState, AGProgram> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGProgramState, AGProgramState, AGProgram> BUILDER = new DbTableBuilder<>("Core", "ProgramState", AGProgramState::new, AGProgramState.class);
	static {
		BUILDER.setTitle(CoreI18n.PROGRAM_STATE);
		BUILDER.setPluralTitle(CoreI18n.PROGRAM_STATES);
	}

	public static final IDbForeignField<AGProgramState, AGProgram> PROGRAM = BUILDER.addForeignField("program", o->o.m_program, (o,v)->o.m_program=v, AGProgram.ID).setTitle(CoreI18n.PROGRAM);
	public static final IDbDayTimeField<AGProgramState> QUEUED_AT = BUILDER.addDayTimeField("queuedAt", o->o.m_queuedAt, (o,v)->o.m_queuedAt=v).setTitle(CoreI18n.QUEUED_AT).setNullable().setDefault(null);
	public static final IDbForeignField<AGProgramState, AGUser> QUEUED_BY = BUILDER.addForeignField("queuedBy", o->o.m_queuedBy, (o,v)->o.m_queuedBy=v, AGUser.ID).setTitle(CoreI18n.QUEUED_BY).setNullable().setDefault(null);
	public static final IDbBooleanField<AGProgramState> ABORT_REQUESTED = BUILDER.addBooleanField("abortRequested", o->o.m_abortRequested, (o,v)->o.m_abortRequested=v).setTitle(CoreI18n.ABORT_REQUESTED).setDefault(false);
	public static final IDbForeignField<AGProgramState, AGProgramExecution> CURRENT_EXECUTION = BUILDER.addForeignField("currentExecution", o->o.m_currentExecution, (o,v)->o.m_currentExecution=v, AGProgramExecution.ID).setTitle(CoreI18n.CURRENT_EXECUTION).setNullable().setDefault(null);
	public static final IDbTableKey<AGProgramState, AGProgram> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(PROGRAM));
	public static final DbRecordTable<AGProgramState, AGProgram> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGProgramState() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getProgramID() {

		return getValueId(PROGRAM);
	}

	public final AGProgram getProgram() {

		return getValue(PROGRAM);
	}

	public final DayTime getQueuedAt() {

		return getValue(QUEUED_AT);
	}

	public final AGProgramState setQueuedAt(DayTime value) {

		return setValue(QUEUED_AT, value);
	}

	public final Integer getQueuedByID() {

		return getValueId(QUEUED_BY);
	}

	public final AGUser getQueuedBy() {

		return getValue(QUEUED_BY);
	}

	public final AGProgramState setQueuedBy(AGUser value) {

		return setValue(QUEUED_BY, value);
	}

	public final Boolean isAbortRequested() {

		return getValue(ABORT_REQUESTED);
	}

	public final AGProgramState setAbortRequested(Boolean value) {

		return setValue(ABORT_REQUESTED, value);
	}

	public final Integer getCurrentExecutionID() {

		return getValueId(CURRENT_EXECUTION);
	}

	public final AGProgramExecution getCurrentExecution() {

		return getValue(CURRENT_EXECUTION);
	}

	public final AGProgramState setCurrentExecution(AGProgramExecution value) {

		return setValue(CURRENT_EXECUTION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGProgramState, AGProgram> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGProgram m_program;
	private DayTime m_queuedAt;
	private AGUser m_queuedBy;
	private Boolean m_abortRequested;
	private AGProgramExecution m_currentExecution;
}

