package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGProgram for
 * database table <i>Core.Program</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGProgramGenerated extends AbstractDbObject<AGProgram> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGProgram, AGProgramGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "Program", AGProgram::new, AGProgram.class);
	static {
		BUILDER.setTitle(CoreI18n.PROGRAM);
		BUILDER.setPluralTitle(CoreI18n.PROGRAMS);
	}

	public static final IDbIdField<AGProgram> ID = BUILDER.addIdFieldForLong("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGProgram, AGUuid> PROGRAM_UUID = BUILDER.addForeignField("programUuid", o->o.m_programUuid, (o,v)->o.m_programUuid=v, AGUuid.ID).setTitle(CoreI18n.PROGRAM_UUID);
	public static final IDbDayTimeField<AGProgram> QUEUED_AT = BUILDER.addDayTimeField("queuedAt", o->o.m_queuedAt, (o,v)->o.m_queuedAt=v).setTitle(CoreI18n.QUEUED_AT).setNullable().setDefault(null);
	public static final IDbForeignField<AGProgram, AGUser> QUEUED_BY = BUILDER.addForeignField("queuedBy", o->o.m_queuedBy, (o,v)->o.m_queuedBy=v, AGUser.ID).setTitle(CoreI18n.QUEUED_BY).setNullable().setDefault(null);
	public static final IDbBooleanField<AGProgram> ABORT_REQUESTED = BUILDER.addBooleanField("abortRequested", o->o.m_abortRequested, (o,v)->o.m_abortRequested=v).setTitle(CoreI18n.ABORT_REQUESTED).setDefault(false);
	public static final IDbForeignField<AGProgram, AGProgramExecution> CURRENT_EXECUTION = BUILDER.addForeignField("currentExecution", o->o.m_currentExecution, (o,v)->o.m_currentExecution=v, AGProgramExecution.ID).setTitle(CoreI18n.CURRENT_EXECUTION).setNullable().setDefault(null);
	public static final IDbKey<AGProgram> UK_PROGRAM_UUID = BUILDER.addUniqueKey("programUuid", PROGRAM_UUID);
	public static final AGProgramTable TABLE = new AGProgramTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGProgram> createSelect() {

		return TABLE.createSelect();
	}

	public static AGProgram get(Integer id) {

		return TABLE.get(id);
	}

	public static AGProgram loadByProgramUuid(AGUuid programUuid) {

		return TABLE//
				.createSelect()
				.where(PROGRAM_UUID.equal(programUuid))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getProgramUuidID() {

		return getValueId(PROGRAM_UUID);
	}

	public final AGUuid getProgramUuid() {

		return getValue(PROGRAM_UUID);
	}

	public final AGProgram setProgramUuid(AGUuid value) {

		return setValue(PROGRAM_UUID, value);
	}

	public final DayTime getQueuedAt() {

		return getValue(QUEUED_AT);
	}

	public final AGProgram setQueuedAt(DayTime value) {

		return setValue(QUEUED_AT, value);
	}

	public final Integer getQueuedByID() {

		return getValueId(QUEUED_BY);
	}

	public final AGUser getQueuedBy() {

		return getValue(QUEUED_BY);
	}

	public final AGProgram setQueuedBy(AGUser value) {

		return setValue(QUEUED_BY, value);
	}

	public final Boolean isAbortRequested() {

		return getValue(ABORT_REQUESTED);
	}

	public final AGProgram setAbortRequested(Boolean value) {

		return setValue(ABORT_REQUESTED, value);
	}

	public final Integer getCurrentExecutionID() {

		return getValueId(CURRENT_EXECUTION);
	}

	public final AGProgramExecution getCurrentExecution() {

		return getValue(CURRENT_EXECUTION);
	}

	public final AGProgram setCurrentExecution(AGProgramExecution value) {

		return setValue(CURRENT_EXECUTION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGProgramTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUuid m_programUuid;
	private DayTime m_queuedAt;
	private AGUser m_queuedBy;
	private Boolean m_abortRequested;
	private AGProgramExecution m_currentExecution;
}

