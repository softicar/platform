package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGProgramExecution for
 * database table <i>Core.ProgramExecution</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGProgramExecutionGenerated extends AbstractDbObject<AGProgramExecution> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGProgramExecution, AGProgramExecutionGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "ProgramExecution", AGProgramExecution::new, AGProgramExecution.class);
	static {
		BUILDER.setTitle(CoreI18n.PROGRAM_EXECUTION);
		BUILDER.setPluralTitle(CoreI18n.PROGRAM_EXECUTIONS);
	}

	public static final IDbIdField<AGProgramExecution> ID = BUILDER.addIdFieldForLong("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGProgramExecution, AGUuid> PROGRAM_UUID = BUILDER.addForeignField("programUuid", o->o.m_programUuid, (o,v)->o.m_programUuid=v, AGUuid.ID).setTitle(CoreI18n.PROGRAM_UUID);
	public static final IDbDayTimeField<AGProgramExecution> STARTED_AT = BUILDER.addDayTimeField("startedAt", o->o.m_startedAt, (o,v)->o.m_startedAt=v).setTitle(CoreI18n.STARTED_AT).setNullable().setDefault(null);
	public static final IDbDayTimeField<AGProgramExecution> TERMINATED_AT = BUILDER.addDayTimeField("terminatedAt", o->o.m_terminatedAt, (o,v)->o.m_terminatedAt=v).setTitle(CoreI18n.TERMINATED_AT).setNullable().setDefault(null);
	public static final IDbBooleanField<AGProgramExecution> FAILED = BUILDER.addBooleanField("failed", o->o.m_failed, (o,v)->o.m_failed=v).setTitle(CoreI18n.FAILED).setDefault(false);
	public static final IDbStringField<AGProgramExecution> OUTPUT = BUILDER.addStringField("output", o->o.m_output, (o,v)->o.m_output=v).setTitle(CoreI18n.OUTPUT).setDefault("").setLengthBits(32);
	public static final AGProgramExecutionTable TABLE = new AGProgramExecutionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGProgramExecution> createSelect() {

		return TABLE.createSelect();
	}

	public static AGProgramExecution get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getProgramUuidID() {

		return getValueId(PROGRAM_UUID);
	}

	public final AGUuid getProgramUuid() {

		return getValue(PROGRAM_UUID);
	}

	public final AGProgramExecution setProgramUuid(AGUuid value) {

		return setValue(PROGRAM_UUID, value);
	}

	public final DayTime getStartedAt() {

		return getValue(STARTED_AT);
	}

	public final AGProgramExecution setStartedAt(DayTime value) {

		return setValue(STARTED_AT, value);
	}

	public final DayTime getTerminatedAt() {

		return getValue(TERMINATED_AT);
	}

	public final AGProgramExecution setTerminatedAt(DayTime value) {

		return setValue(TERMINATED_AT, value);
	}

	public final Boolean isFailed() {

		return getValue(FAILED);
	}

	public final AGProgramExecution setFailed(Boolean value) {

		return setValue(FAILED, value);
	}

	public final String getOutput() {

		return getValue(OUTPUT);
	}

	public final AGProgramExecution setOutput(String value) {

		return setValue(OUTPUT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGProgramExecutionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUuid m_programUuid;
	private DayTime m_startedAt;
	private DayTime m_terminatedAt;
	private Boolean m_failed;
	private String m_output;
}

