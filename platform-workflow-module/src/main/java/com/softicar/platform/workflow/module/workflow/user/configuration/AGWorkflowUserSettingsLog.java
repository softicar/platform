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
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowUserSettingsLog for
 * database table <i>Workflow.WorkflowUserSettingsLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowUserSettingsLog extends AbstractDbRecord<AGWorkflowUserSettingsLog, Tuple2<AGWorkflowUserSettings, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowUserSettingsLog, AGWorkflowUserSettingsLog, Tuple2<AGWorkflowUserSettings, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowUserSettingsLog", AGWorkflowUserSettingsLog::new, AGWorkflowUserSettingsLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_USER_SETTINGS_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_USER_SETTINGS_LOGS);
	}

	public static final IDbForeignRowField<AGWorkflowUserSettingsLog, AGWorkflowUserSettings, AGUser> WORKFLOW_USER_SETTINGS = BUILDER.addForeignRowField("workflowUserSettings", o->o.m_workflowUserSettings, (o,v)->o.m_workflowUserSettings=v, AGWorkflowUserSettings.USER).setTitle(WorkflowI18n.WORKFLOW_USER_SETTINGS);
	public static final IDbForeignField<AGWorkflowUserSettingsLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbBooleanField<AGWorkflowUserSettingsLog> EMAIL_NOTIFICATIONS_FOR_NEW_TASKS = BUILDER.addBooleanField("emailNotificationsForNewTasks", o->o.m_emailNotificationsForNewTasks, (o,v)->o.m_emailNotificationsForNewTasks=v).setTitle(WorkflowI18n.EMAIL_NOTIFICATIONS_FOR_NEW_TASKS).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowUserSettingsLog, AGUser> SUBSTITUTE = BUILDER.addForeignField("substitute", o->o.m_substitute, (o,v)->o.m_substitute=v, AGUser.ID).setTitle(WorkflowI18n.SUBSTITUTE).setNullable().setDefault(null);
	public static final IDbDayField<AGWorkflowUserSettingsLog> SUBSTITUTE_FROM = BUILDER.addDayField("substituteFrom", o->o.m_substituteFrom, (o,v)->o.m_substituteFrom=v).setTitle(WorkflowI18n.SUBSTITUTE_FROM).setNullable().setDefault(null);
	public static final IDbDayField<AGWorkflowUserSettingsLog> SUBSTITUTE_TO = BUILDER.addDayField("substituteTo", o->o.m_substituteTo, (o,v)->o.m_substituteTo=v).setTitle(WorkflowI18n.SUBSTITUTE_TO).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowUserSettingsLog, Tuple2<AGWorkflowUserSettings, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_USER_SETTINGS, TRANSACTION));
	public static final DbRecordTable<AGWorkflowUserSettingsLog, Tuple2<AGWorkflowUserSettings, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowUserSettingsLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGWorkflowUserSettings getWorkflowUserSettings() {

		return getValue(WORKFLOW_USER_SETTINGS);
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

	public final AGWorkflowUserSettingsLog setEmailNotificationsForNewTasks(Boolean value) {

		return setValue(EMAIL_NOTIFICATIONS_FOR_NEW_TASKS, value);
	}

	public final Integer getSubstituteID() {

		return getValueId(SUBSTITUTE);
	}

	public final AGUser getSubstitute() {

		return getValue(SUBSTITUTE);
	}

	public final AGWorkflowUserSettingsLog setSubstitute(AGUser value) {

		return setValue(SUBSTITUTE, value);
	}

	public final Day getSubstituteFrom() {

		return getValue(SUBSTITUTE_FROM);
	}

	public final AGWorkflowUserSettingsLog setSubstituteFrom(Day value) {

		return setValue(SUBSTITUTE_FROM, value);
	}

	public final Day getSubstituteTo() {

		return getValue(SUBSTITUTE_TO);
	}

	public final AGWorkflowUserSettingsLog setSubstituteTo(Day value) {

		return setValue(SUBSTITUTE_TO, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowUserSettingsLog, Tuple2<AGWorkflowUserSettings, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowUserSettings m_workflowUserSettings;
	private AGTransaction m_transaction;
	private Boolean m_emailNotificationsForNewTasks;
	private AGUser m_substitute;
	private Day m_substituteFrom;
	private Day m_substituteTo;
}

