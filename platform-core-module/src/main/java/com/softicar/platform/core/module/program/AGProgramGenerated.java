package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
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
	public static final IDbIntegerField<AGProgram> EXECUTION_RETENTION_DAYS = BUILDER.addIntegerField("executionRetentionDays", o->o.m_executionRetentionDays, (o,v)->o.m_executionRetentionDays=v).setTitle(CoreI18n.EXECUTION_RETENTION_DAYS).setDefault(28);
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
				.where(PROGRAM_UUID.isEqual(programUuid))
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

	public final Integer getExecutionRetentionDays() {

		return getValue(EXECUTION_RETENTION_DAYS);
	}

	public final AGProgram setExecutionRetentionDays(Integer value) {

		return setValue(EXECUTION_RETENTION_DAYS, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGProgramTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUuid m_programUuid;
	private Integer m_executionRetentionDays;
}

