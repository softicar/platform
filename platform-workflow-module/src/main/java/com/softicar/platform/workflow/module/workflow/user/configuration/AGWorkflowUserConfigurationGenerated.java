package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowUserConfiguration for
 * database table <i>Workflow.WorkflowUserConfiguration</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowUserConfigurationGenerated extends AbstractDbRecord<AGWorkflowUserConfiguration, AGUser> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowUserConfiguration, AGWorkflowUserConfigurationGenerated, AGUser> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowUserConfiguration", AGWorkflowUserConfiguration::new, AGWorkflowUserConfiguration.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_USER_CONFIGURATION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_USER_CONFIGURATIONS);
	}

	public static final IDbForeignField<AGWorkflowUserConfiguration, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(WorkflowI18n.USER).setForeignKeyName("WorkflowUserConfiguration_ibfk_1");
	public static final IDbBooleanField<AGWorkflowUserConfiguration> EMAIL_NOTIFICATIONS_FOR_NEW_TASKS = BUILDER.addBooleanField("emailNotificationsForNewTasks", o->o.m_emailNotificationsForNewTasks, (o,v)->o.m_emailNotificationsForNewTasks=v).setTitle(WorkflowI18n.EMAIL_NOTIFICATIONS_FOR_NEW_TASKS).setDefault(true);
	public static final IDbForeignField<AGWorkflowUserConfiguration, AGUser> SUBSTITUTE = BUILDER.addForeignField("substitute", o->o.m_substitute, (o,v)->o.m_substitute=v, AGUser.ID).setTitle(WorkflowI18n.SUBSTITUTE).setNullable().setDefault(null).setForeignKeyName("WorkflowUserConfiguration_ibfk_2");
	public static final IDbDayField<AGWorkflowUserConfiguration> SUBSTITUTE_FROM = BUILDER.addDayField("substituteFrom", o->o.m_substituteFrom, (o,v)->o.m_substituteFrom=v).setTitle(WorkflowI18n.SUBSTITUTE_FROM).setNullable().setDefault(null);
	public static final IDbDayField<AGWorkflowUserConfiguration> SUBSTITUTE_TO = BUILDER.addDayField("substituteTo", o->o.m_substituteTo, (o,v)->o.m_substituteTo=v).setTitle(WorkflowI18n.SUBSTITUTE_TO).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowUserConfiguration, AGUser> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(USER));
	public static final IDbKey<AGWorkflowUserConfiguration> IK_SUBSTITUTE = BUILDER.addIndexKey("substitute", SUBSTITUTE);
	public static final AGWorkflowUserConfigurationTable TABLE = new AGWorkflowUserConfigurationTable(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowUserConfigurationGenerated() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final Boolean isEmailNotificationsForNewTasks() {

		return getValue(EMAIL_NOTIFICATIONS_FOR_NEW_TASKS);
	}

	public final AGWorkflowUserConfiguration setEmailNotificationsForNewTasks(Boolean value) {

		return setValue(EMAIL_NOTIFICATIONS_FOR_NEW_TASKS, value);
	}

	public final Integer getSubstituteID() {

		return getValueId(SUBSTITUTE);
	}

	public final AGUser getSubstitute() {

		return getValue(SUBSTITUTE);
	}

	public final AGWorkflowUserConfiguration setSubstitute(AGUser value) {

		return setValue(SUBSTITUTE, value);
	}

	public final Day getSubstituteFrom() {

		return getValue(SUBSTITUTE_FROM);
	}

	public final AGWorkflowUserConfiguration setSubstituteFrom(Day value) {

		return setValue(SUBSTITUTE_FROM, value);
	}

	public final Day getSubstituteTo() {

		return getValue(SUBSTITUTE_TO);
	}

	public final AGWorkflowUserConfiguration setSubstituteTo(Day value) {

		return setValue(SUBSTITUTE_TO, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowUserConfigurationTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGUser m_user;
	private Boolean m_emailNotificationsForNewTasks;
	private AGUser m_substitute;
	private Day m_substituteFrom;
	private Day m_substituteTo;
}

