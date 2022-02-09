package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
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

	public static final IDbForeignField<AGWorkflowUserConfiguration, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(WorkflowI18n.USER);
	public static final IDbBooleanField<AGWorkflowUserConfiguration> NOTIFY = BUILDER.addBooleanField("notify", o->o.m_notify, (o,v)->o.m_notify=v).setTitle(WorkflowI18n.NOTIFY).setDefault(true);
	public static final IDbForeignField<AGWorkflowUserConfiguration, AGUser> SUBSTITUTE = BUILDER.addForeignField("substitute", o->o.m_substitute, (o,v)->o.m_substitute=v, AGUser.ID).setTitle(WorkflowI18n.SUBSTITUTE).setNullable().setDefault(null);
	public static final IDbDayField<AGWorkflowUserConfiguration> VALID_FROM = BUILDER.addDayField("validFrom", o->o.m_validFrom, (o,v)->o.m_validFrom=v).setTitle(WorkflowI18n.VALID_FROM).setNullable().setDefault(null);
	public static final IDbDayField<AGWorkflowUserConfiguration> VALID_TO = BUILDER.addDayField("validTo", o->o.m_validTo, (o,v)->o.m_validTo=v).setTitle(WorkflowI18n.VALID_TO).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowUserConfiguration, AGUser> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(USER));
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

	public final Boolean isNotify() {

		return getValue(NOTIFY);
	}

	public final AGWorkflowUserConfiguration setNotify(Boolean value) {

		return setValue(NOTIFY, value);
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

	public final Day getValidFrom() {

		return getValue(VALID_FROM);
	}

	public final AGWorkflowUserConfiguration setValidFrom(Day value) {

		return setValue(VALID_FROM, value);
	}

	public final Day getValidTo() {

		return getValue(VALID_TO);
	}

	public final AGWorkflowUserConfiguration setValidTo(Day value) {

		return setValue(VALID_TO, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowUserConfigurationTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGUser m_user;
	private Boolean m_notify;
	private AGUser m_substitute;
	private Day m_validFrom;
	private Day m_validTo;
}

