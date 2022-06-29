package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGScheduledProgramExecution for
 * database table <i>Core.ScheduledProgramExecution</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGScheduledProgramExecutionGenerated extends AbstractDbObject<AGScheduledProgramExecution> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGScheduledProgramExecution, AGScheduledProgramExecutionGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "ScheduledProgramExecution", AGScheduledProgramExecution::new, AGScheduledProgramExecution.class);
	static {
		BUILDER.setTitle(CoreI18n.SCHEDULED_PROGRAM_EXECUTION);
		BUILDER.setPluralTitle(CoreI18n.SCHEDULED_PROGRAM_EXECUTIONS);
	}

	public static final IDbIdField<AGScheduledProgramExecution> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGScheduledProgramExecution> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbStringField<AGScheduledProgramExecution> CRON_EXPRESSION = BUILDER.addStringField("cronExpression", o->o.m_cronExpression, (o,v)->o.m_cronExpression=v).setTitle(CoreI18n.CRON_EXPRESSION).setMaximumLength(255);
	public static final IDbForeignField<AGScheduledProgramExecution, AGUuid> PROGRAM_UUID = BUILDER.addForeignField("programUuid", o->o.m_programUuid, (o,v)->o.m_programUuid=v, AGUuid.ID).setTitle(CoreI18n.PROGRAM_UUID);
	public static final IDbIntegerField<AGScheduledProgramExecution> MAXIMUM_RUNTIME = BUILDER.addIntegerField("maximumRuntime", o->o.m_maximumRuntime, (o,v)->o.m_maximumRuntime=v).setTitle(CoreI18n.MAXIMUM_RUNTIME).setNullable().setDefault(null);
	public static final IDbBooleanField<AGScheduledProgramExecution> AUTOMATIC_ABORT = BUILDER.addBooleanField("automaticAbort", o->o.m_automaticAbort, (o,v)->o.m_automaticAbort=v).setTitle(CoreI18n.AUTOMATIC_ABORT).setDefault(false);
	public static final IDbKey<AGScheduledProgramExecution> UK_PROGRAM_UUID = BUILDER.addUniqueKey("programUuid", PROGRAM_UUID);
	public static final AGScheduledProgramExecutionTable TABLE = new AGScheduledProgramExecutionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGScheduledProgramExecution> createSelect() {

		return TABLE.createSelect();
	}

	public static AGScheduledProgramExecution get(Integer id) {

		return TABLE.get(id);
	}

	public static AGScheduledProgramExecution loadByProgramUuid(AGUuid programUuid) {

		return TABLE//
				.createSelect()
				.where(PROGRAM_UUID.equal(programUuid))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGScheduledProgramExecution setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getCronExpression() {

		return getValue(CRON_EXPRESSION);
	}

	public final AGScheduledProgramExecution setCronExpression(String value) {

		return setValue(CRON_EXPRESSION, value);
	}

	public final Integer getProgramUuidID() {

		return getValueId(PROGRAM_UUID);
	}

	public final AGUuid getProgramUuid() {

		return getValue(PROGRAM_UUID);
	}

	public final AGScheduledProgramExecution setProgramUuid(AGUuid value) {

		return setValue(PROGRAM_UUID, value);
	}

	public final Integer getMaximumRuntime() {

		return getValue(MAXIMUM_RUNTIME);
	}

	public final AGScheduledProgramExecution setMaximumRuntime(Integer value) {

		return setValue(MAXIMUM_RUNTIME, value);
	}

	public final Boolean isAutomaticAbort() {

		return getValue(AUTOMATIC_ABORT);
	}

	public final AGScheduledProgramExecution setAutomaticAbort(Boolean value) {

		return setValue(AUTOMATIC_ABORT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGScheduledProgramExecutionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private String m_cronExpression;
	private AGUuid m_programUuid;
	private Integer m_maximumRuntime;
	private Boolean m_automaticAbort;
}

