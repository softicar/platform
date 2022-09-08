package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

/**
 * This is the automatically generated class AGWorkflowLog for
 * database table <i>Workflow.WorkflowLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowLog extends AbstractDbRecord<AGWorkflowLog, Tuple2<AGWorkflow, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowLog, AGWorkflowLog, Tuple2<AGWorkflow, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowLog", AGWorkflowLog::new, AGWorkflowLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_LOGS);
	}

	public static final IDbForeignField<AGWorkflowLog, AGWorkflow> WORKFLOW = BUILDER.addForeignField("workflow", o->o.m_workflow, (o,v)->o.m_workflow=v, AGWorkflow.ID).setTitle(WorkflowI18n.WORKFLOW).setForeignKeyName("WorkflowLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setForeignKeyName("WorkflowLog_ibfk_2");
	public static final IDbStringField<AGWorkflowLog> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbBooleanField<AGWorkflowLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowLog, AGWorkflowVersion> CURRENT_VERSION = BUILDER.addForeignField("currentVersion", o->o.m_currentVersion, (o,v)->o.m_currentVersion=v, AGWorkflowVersion.ID).setTitle(WorkflowI18n.CURRENT_VERSION).setNullable().setDefault(null).setForeignKeyName("WorkflowLog_ibfk_3");
	public static final IDbTableKey<AGWorkflowLog, Tuple2<AGWorkflow, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW, TRANSACTION));
	public static final IDbKey<AGWorkflowLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGWorkflowLog> IK_CURRENT_VERSION = BUILDER.addIndexKey("currentVersion", CURRENT_VERSION);
	public static final DbRecordTable<AGWorkflowLog, Tuple2<AGWorkflow, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowID() {

		return getValueId(WORKFLOW);
	}

	public final AGWorkflow getWorkflow() {

		return getValue(WORKFLOW);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWorkflowLog setName(String value) {

		return setValue(NAME, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getCurrentVersionID() {

		return getValueId(CURRENT_VERSION);
	}

	public final AGWorkflowVersion getCurrentVersion() {

		return getValue(CURRENT_VERSION);
	}

	public final AGWorkflowLog setCurrentVersion(AGWorkflowVersion value) {

		return setValue(CURRENT_VERSION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowLog, Tuple2<AGWorkflow, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflow m_workflow;
	private AGTransaction m_transaction;
	private String m_name;
	private Boolean m_active;
	private AGWorkflowVersion m_currentVersion;
}

