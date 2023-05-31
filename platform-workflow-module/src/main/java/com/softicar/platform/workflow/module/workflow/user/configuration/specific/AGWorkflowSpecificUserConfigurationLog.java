package com.softicar.platform.workflow.module.workflow.user.configuration.specific;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowSpecificUserConfigurationLog for
 * database table <i>Workflow.WorkflowSpecificUserConfigurationLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowSpecificUserConfigurationLog extends AbstractDbRecord<AGWorkflowSpecificUserConfigurationLog, Tuple2<AGWorkflowSpecificUserConfiguration, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowSpecificUserConfigurationLog, AGWorkflowSpecificUserConfigurationLog, Tuple2<AGWorkflowSpecificUserConfiguration, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowSpecificUserConfigurationLog", AGWorkflowSpecificUserConfigurationLog::new, AGWorkflowSpecificUserConfigurationLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_SPECIFIC_USER_CONFIGURATION_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_SPECIFIC_USER_CONFIGURATION_LOGS);
	}

	public static final IDbForeignField<AGWorkflowSpecificUserConfigurationLog, AGWorkflowSpecificUserConfiguration> CONFIGURATION = BUILDER.addForeignField("configuration", o->o.m_configuration, (o,v)->o.m_configuration=v, AGWorkflowSpecificUserConfiguration.ID).setTitle(WorkflowI18n.CONFIGURATION).setCascade(true, true).setForeignKeyName("WorkflowSpecificUserConfigurationLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowSpecificUserConfigurationLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("WorkflowSpecificUserConfigurationLog_ibfk_2");
	public static final IDbBooleanField<AGWorkflowSpecificUserConfigurationLog> SUBSCRIBED = BUILDER.addBooleanField("subscribed", o->o.m_subscribed, (o,v)->o.m_subscribed=v).setTitle(WorkflowI18n.SUBSCRIBED).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowSpecificUserConfigurationLog, Tuple2<AGWorkflowSpecificUserConfiguration, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(CONFIGURATION, TRANSACTION));
	public static final IDbKey<AGWorkflowSpecificUserConfigurationLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGWorkflowSpecificUserConfigurationLog, Tuple2<AGWorkflowSpecificUserConfiguration, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowSpecificUserConfigurationLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getConfigurationID() {

		return getValueId(CONFIGURATION);
	}

	public final AGWorkflowSpecificUserConfiguration getConfiguration() {

		return getValue(CONFIGURATION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Boolean isSubscribed() {

		return getValue(SUBSCRIBED);
	}

	public final AGWorkflowSpecificUserConfigurationLog setSubscribed(Boolean value) {

		return setValue(SUBSCRIBED, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowSpecificUserConfigurationLog, Tuple2<AGWorkflowSpecificUserConfiguration, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowSpecificUserConfiguration m_configuration;
	private AGTransaction m_transaction;
	private Boolean m_subscribed;
}

