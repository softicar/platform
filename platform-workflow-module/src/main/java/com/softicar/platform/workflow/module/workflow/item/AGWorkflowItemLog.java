package com.softicar.platform.workflow.module.workflow.item;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

/**
 * This is the automatically generated class AGWorkflowItemLog for
 * database table <i>Workflow.WorkflowItemLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowItemLog extends AbstractDbRecord<AGWorkflowItemLog, Tuple2<AGWorkflowItem, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowItemLog, AGWorkflowItemLog, Tuple2<AGWorkflowItem, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowItemLog", AGWorkflowItemLog::new, AGWorkflowItemLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_ITEM_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_ITEM_LOGS);
	}

	public static final IDbForeignField<AGWorkflowItemLog, AGWorkflowItem> WORKFLOW_ITEM = BUILDER.addForeignField("workflowItem", o->o.m_workflowItem, (o,v)->o.m_workflowItem=v, AGWorkflowItem.ID).setTitle(WorkflowI18n.WORKFLOW_ITEM).setForeignKeyName("WorkflowItemLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowItemLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setForeignKeyName("WorkflowItemLog_ibfk_2");
	public static final IDbForeignField<AGWorkflowItemLog, AGWorkflowNode> WORKFLOW_NODE = BUILDER.addForeignField("workflowNode", o->o.m_workflowNode, (o,v)->o.m_workflowNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.WORKFLOW_NODE).setNullable().setDefault(null).setForeignKeyName("WorkflowItemLog_ibfk_3");
	public static final IDbTableKey<AGWorkflowItemLog, Tuple2<AGWorkflowItem, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_ITEM, TRANSACTION));
	public static final IDbKey<AGWorkflowItemLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGWorkflowItemLog> IK_WORKFLOW_NODE = BUILDER.addIndexKey("workflowNode", WORKFLOW_NODE);
	public static final DbRecordTable<AGWorkflowItemLog, Tuple2<AGWorkflowItem, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowItemLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowItemID() {

		return getValueId(WORKFLOW_ITEM);
	}

	public final AGWorkflowItem getWorkflowItem() {

		return getValue(WORKFLOW_ITEM);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Integer getWorkflowNodeID() {

		return getValueId(WORKFLOW_NODE);
	}

	public final AGWorkflowNode getWorkflowNode() {

		return getValue(WORKFLOW_NODE);
	}

	public final AGWorkflowItemLog setWorkflowNode(AGWorkflowNode value) {

		return setValue(WORKFLOW_NODE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowItemLog, Tuple2<AGWorkflowItem, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowItem m_workflowItem;
	private AGTransaction m_transaction;
	private AGWorkflowNode m_workflowNode;
}

