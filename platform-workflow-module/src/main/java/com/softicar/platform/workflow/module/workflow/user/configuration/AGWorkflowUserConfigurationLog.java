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

	public static final IDbForeignRowField<AGWorkflowUserConfigurationLog, AGWorkflowUserConfiguration, AGUser> WORKFLOW_USER_CONFIGURATION = BUILDER.addForeignRowField("workflowUserConfiguration", o->o.m_workflowUserConfiguration, (o,v)->o.m_workflowUserConfiguration=v, AGWorkflowUserConfiguration.USER).setTitle(WorkflowI18n.WORKFLOW_USER_CONFIGURATION);
	public static final IDbForeignField<AGWorkflowUserConfigurationLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbBooleanField<AGWorkflowUserConfigurationLog> NOTIFY = BUILDER.addBooleanField("notify", o->o.m_notify, (o,v)->o.m_notify=v).setTitle(WorkflowI18n.NOTIFY).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowUserConfigurationLog, AGUser> SUBSTITUTE = BUILDER.addForeignField("substitute", o->o.m_substitute, (o,v)->o.m_substitute=v, AGUser.ID).setTitle(WorkflowI18n.SUBSTITUTE).setNullable().setDefault(null);
	public static final IDbDayField<AGWorkflowUserConfigurationLog> VALID_FROM = BUILDER.addDayField("validFrom", o->o.m_validFrom, (o,v)->o.m_validFrom=v).setTitle(WorkflowI18n.VALID_FROM).setNullable().setDefault(null);
	public static final IDbDayField<AGWorkflowUserConfigurationLog> VALID_TO = BUILDER.addDayField("validTo", o->o.m_validTo, (o,v)->o.m_validTo=v).setTitle(WorkflowI18n.VALID_TO).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowUserConfigurationLog, Tuple2<AGWorkflowUserConfiguration, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_USER_CONFIGURATION, TRANSACTION));
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

	public final Boolean isNotify() {

		return getValue(NOTIFY);
	}

	public final AGWorkflowUserConfigurationLog setNotify(Boolean value) {

		return setValue(NOTIFY, value);
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

	public final Day getValidFrom() {

		return getValue(VALID_FROM);
	}

	public final AGWorkflowUserConfigurationLog setValidFrom(Day value) {

		return setValue(VALID_FROM, value);
	}

	public final Day getValidTo() {

		return getValue(VALID_TO);
	}

	public final AGWorkflowUserConfigurationLog setValidTo(Day value) {

		return setValue(VALID_TO, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowUserConfigurationLog, Tuple2<AGWorkflowUserConfiguration, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowUserConfiguration m_workflowUserConfiguration;
	private AGTransaction m_transaction;
	private Boolean m_notify;
	private AGUser m_substitute;
	private Day m_validFrom;
	private Day m_validTo;
}

