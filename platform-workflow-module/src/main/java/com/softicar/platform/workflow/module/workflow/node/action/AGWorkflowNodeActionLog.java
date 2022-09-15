package com.softicar.platform.workflow.module.workflow.node.action;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.uuid.AGUuid;
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
 * This is the automatically generated class AGWorkflowNodeActionLog for
 * database table <i>Workflow.WorkflowNodeActionLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowNodeActionLog extends AbstractDbRecord<AGWorkflowNodeActionLog, Tuple2<AGWorkflowNodeAction, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowNodeActionLog, AGWorkflowNodeActionLog, Tuple2<AGWorkflowNodeAction, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowNodeActionLog", AGWorkflowNodeActionLog::new, AGWorkflowNodeActionLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_LOGS);
	}

	public static final IDbForeignField<AGWorkflowNodeActionLog, AGWorkflowNodeAction> WORKFLOW_NODE_ACTION = BUILDER.addForeignField("workflowNodeAction", o->o.m_workflowNodeAction, (o,v)->o.m_workflowNodeAction=v, AGWorkflowNodeAction.ID).setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION).setCascade(true, true).setForeignKeyName("WorkflowNodeActionLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowNodeActionLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("WorkflowNodeActionLog_ibfk_2");
	public static final IDbBooleanField<AGWorkflowNodeActionLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowNodeActionLog, AGUuid> ACTION = BUILDER.addForeignField("action", o->o.m_action, (o,v)->o.m_action=v, AGUuid.ID).setTitle(WorkflowI18n.ACTION).setNullable().setDefault(null).setForeignKeyName("WorkflowNodeActionLog_ibfk_3");
	public static final IDbTableKey<AGWorkflowNodeActionLog, Tuple2<AGWorkflowNodeAction, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_NODE_ACTION, TRANSACTION));
	public static final IDbKey<AGWorkflowNodeActionLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGWorkflowNodeActionLog> IK_ACTION = BUILDER.addIndexKey("action", ACTION);
	public static final DbRecordTable<AGWorkflowNodeActionLog, Tuple2<AGWorkflowNodeAction, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowNodeActionLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowNodeActionID() {

		return getValueId(WORKFLOW_NODE_ACTION);
	}

	public final AGWorkflowNodeAction getWorkflowNodeAction() {

		return getValue(WORKFLOW_NODE_ACTION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowNodeActionLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getActionID() {

		return getValueId(ACTION);
	}

	public final AGUuid getAction() {

		return getValue(ACTION);
	}

	public final AGWorkflowNodeActionLog setAction(AGUuid value) {

		return setValue(ACTION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowNodeActionLog, Tuple2<AGWorkflowNodeAction, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowNodeAction m_workflowNodeAction;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGUuid m_action;
}

