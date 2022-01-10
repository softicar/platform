package com.softicar.platform.core.module.program.execution.manual;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGProgramManualExecution for
 * database table <i>Core.ProgramManualExecution</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGProgramManualExecutionGenerated extends AbstractDbObject<AGProgramManualExecution> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGProgramManualExecution, AGProgramManualExecutionGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "ProgramManualExecution", AGProgramManualExecution::new, AGProgramManualExecution.class);
	static {
		BUILDER.setTitle(CoreI18n.PROGRAM_MANUAL_EXECUTION);
		BUILDER.setPluralTitle(CoreI18n.PROGRAM_MANUAL_EXECUTIONS);
	}

	public static final IDbIdField<AGProgramManualExecution> ID = BUILDER.addIdFieldForLong("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGProgramManualExecution, AGProgram> PROGRAM = BUILDER.addForeignField("program", o->o.m_program, (o,v)->o.m_program=v, AGProgram.ID).setTitle(CoreI18n.PROGRAM);
	public static final IDbForeignField<AGProgramManualExecution, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER);
	public static final IDbDayTimeField<AGProgramManualExecution> TIME = BUILDER.addDayTimeField("time", o->o.m_time, (o,v)->o.m_time=v).setTitle(CoreI18n.TIME);
	public static final AGProgramManualExecutionTable TABLE = new AGProgramManualExecutionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGProgramManualExecution> createSelect() {

		return TABLE.createSelect();
	}

	public static AGProgramManualExecution get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getProgramID() {

		return getValueId(PROGRAM);
	}

	public final AGProgram getProgram() {

		return getValue(PROGRAM);
	}

	public final AGProgramManualExecution setProgram(AGProgram value) {

		return setValue(PROGRAM, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGProgramManualExecution setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final DayTime getTime() {

		return getValue(TIME);
	}

	public final AGProgramManualExecution setTime(DayTime value) {

		return setValue(TIME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGProgramManualExecutionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGProgram m_program;
	private AGUser m_user;
	private DayTime m_time;
}

