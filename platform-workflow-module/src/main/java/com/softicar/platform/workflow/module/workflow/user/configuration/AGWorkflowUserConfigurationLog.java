package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowUserConfigurationLog for
 * database table <i>Workflow.WorkflowUserConfigurationLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowUserConfigurationLog extends AbstractDbRecord<AGWorkflowUserConfigurationLog, Tuple2<AGWorkflowUserConfiguration, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowUserConfigurationLog, AGWorkflowUserConfigurationLog, Tuple2<AGWorkflowUserConfiguration, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowUserConfigurationLog", AGWorkflowUserConfigurationLog::new, AGWorkflowUserConfigurationLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_USER_CONFIGURATION_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_USER_CONFIGURATION_LOGS);
	}

	public static final IDbForeignRowField<AGWorkflowUserConfigurationLog, AGWorkflowUserConfiguration, AGUser> WORKFLOW_USER_CONFIGURATION = BUILDER.addForeignRowField("workflowUserConfiguration", o->o.m_workflowUserConfiguration, (o,v)->o.m_workflowUserConfiguration=v, AGWorkflowUserConfiguration.USER).setTitle(WorkflowI18n.WORKFLOW_USER_CONFIGURATION).setForeignKeyName("WorkflowUserConfigurationLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowUserConfigurationLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setForeignKeyName("WorkflowUserConfigurationLog_ibfk_2");
	public static final IDbBooleanField<AGWorkflowUserConfigurationLog> EMAIL_NOTIFICATIONS_FOR_NEW_TASKS = BUILDER.addBooleanField("emailNotificationsForNewTasks", o->o.m_emailNotificationsForNewTasks, (o,v)->o.m_emailNotificationsForNewTasks=v).setTitle(WorkflowI18n.EMAIL_NOTIFICATIONS_FOR_NEW_TASKS).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowUserConfigurationLog, AGUser> SUBSTITUTE = BUILDER.addForeignField("substitute", o->o.m_substitute, (o,v)->o.m_substitute=v, AGUser.ID).setTitle(WorkflowI18n.SUBSTITUTE).setNullable().setDefault(null).setForeignKeyName("WorkflowUserConfigurationLog_ibfk_3");
	public static final IDbDayField<AGWorkflowUserConfigurationLog> SUBSTITUTE_FROM = BUILDER.addDayField("substituteFrom", o->o.m_substituteFrom, (o,v)->o.m_substituteFrom=v).setTitle(WorkflowI18n.SUBSTITUTE_FROM).setNullable().setDefault(null);
	public static final IDbDayField<AGWorkflowUserConfigurationLog> SUBSTITUTE_TO = BUILDER.addDayField("substituteTo", o->o.m_substituteTo, (o,v)->o.m_substituteTo=v).setTitle(WorkflowI18n.SUBSTITUTE_TO).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowUserConfigurationLog, Tuple2<AGWorkflowUserConfiguration, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_USER_CONFIGURATION, TRANSACTION));
	public static final IDbKey<AGWorkflowUserConfigurationLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGWorkflowUserConfigurationLog> IK_SUBSTITUTE = BUILDER.addIndexKey("substitute", SUBSTITUTE);
	public static final DbRecordTable<AGWorkflowUserConfigurationLog, Tuple2<AGWorkflowUserConfiguration, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowUserConfigurationLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGWorkflowUserConfiguration getWorkflowUserConfiguration() {

		return getValue(WORKFLOW_USER_CONFIGURATION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Boolean isEmailNotificationsForNewTasks() {

		return getValue(EMAIL_NOTIFICATIONS_FOR_NEW_TASKS);
	}

	public final AGWorkflowUserConfigurationLog setEmailNotificationsForNewTasks(Boolean value) {

		return setValue(EMAIL_NOTIFICATIONS_FOR_NEW_TASKS, value);
	}

	public final Integer getSubstituteID() {

		return getValueId(SUBSTITUTE);
	}

	public final AGUser getSubstitute() {

		return getValue(SUBSTITUTE);
	}

	public final AGWorkflowUserConfigurationLog setSubstitute(AGUser value) {

		return setValue(SUBSTITUTE, value);
	}

	public final Day getSubstituteFrom() {

		return getValue(SUBSTITUTE_FROM);
	}

	public final AGWorkflowUserConfigurationLog setSubstituteFrom(Day value) {

		return setValue(SUBSTITUTE_FROM, value);
	}

	public final Day getSubstituteTo() {

		return getValue(SUBSTITUTE_TO);
	}

	public final AGWorkflowUserConfigurationLog setSubstituteTo(Day value) {

		return setValue(SUBSTITUTE_TO, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowUserConfigurationLog, Tuple2<AGWorkflowUserConfiguration, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowUserConfiguration m_workflowUserConfiguration;
	private AGTransaction m_transaction;
	private Boolean m_emailNotificationsForNewTasks;
	private AGUser m_substitute;
	private Day m_substituteFrom;
	private Day m_substituteTo;
}

