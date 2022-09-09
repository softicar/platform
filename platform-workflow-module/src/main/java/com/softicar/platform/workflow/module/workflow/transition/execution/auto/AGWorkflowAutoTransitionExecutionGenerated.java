package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;

/**
 * This is the automatically generated class AGWorkflowAutoTransitionExecution for
 * database table <i>Workflow.WorkflowAutoTransitionExecution</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowAutoTransitionExecutionGenerated extends AbstractDbObject<AGWorkflowAutoTransitionExecution> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowAutoTransitionExecution, AGWorkflowAutoTransitionExecutionGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowAutoTransitionExecution", AGWorkflowAutoTransitionExecution::new, AGWorkflowAutoTransitionExecution.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_AUTO_TRANSITION_EXECUTION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_AUTO_TRANSITION_EXECUTIONS);
	}

	public static final IDbIdField<AGWorkflowAutoTransitionExecution> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowAutoTransitionExecution, AGWorkflowItem> WORKFLOW_ITEM = BUILDER.addForeignField("workflowItem", o->o.m_workflowItem, (o,v)->o.m_workflowItem=v, AGWorkflowItem.ID).setTitle(WorkflowI18n.WORKFLOW_ITEM).setForeignKeyName("WorkflowAutoTransitionExecution_ibfk_1");
	public static final IDbForeignField<AGWorkflowAutoTransitionExecution, AGWorkflowTransition> WORKFLOW_TRANSITION = BUILDER.addForeignField("workflowTransition", o->o.m_workflowTransition, (o,v)->o.m_workflowTransition=v, AGWorkflowTransition.ID).setTitle(WorkflowI18n.WORKFLOW_TRANSITION).setForeignKeyName("WorkflowAutoTransitionExecution_ibfk_2");
	public static final IDbForeignField<AGWorkflowAutoTransitionExecution, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setForeignKeyName("WorkflowAutoTransitionExecution_ibfk_3");
	public static final IDbKey<AGWorkflowAutoTransitionExecution> IK_WORKFLOW_ITEM = BUILDER.addIndexKey("workflowItem", WORKFLOW_ITEM);
	public static final IDbKey<AGWorkflowAutoTransitionExecution> IK_WORKFLOW_TRANSITION = BUILDER.addIndexKey("workflowTransition", WORKFLOW_TRANSITION);
	public static final IDbKey<AGWorkflowAutoTransitionExecution> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final AGWorkflowAutoTransitionExecutionTable TABLE = new AGWorkflowAutoTransitionExecutionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowAutoTransitionExecution> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowAutoTransitionExecution get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowItemID() {

		return getValueId(WORKFLOW_ITEM);
	}

	public final AGWorkflowItem getWorkflowItem() {

		return getValue(WORKFLOW_ITEM);
	}

	public final AGWorkflowAutoTransitionExecution setWorkflowItem(AGWorkflowItem value) {

		return setValue(WORKFLOW_ITEM, value);
	}

	public final Integer getWorkflowTransitionID() {

		return getValueId(WORKFLOW_TRANSITION);
	}

	public final AGWorkflowTransition getWorkflowTransition() {

		return getValue(WORKFLOW_TRANSITION);
	}

	public final AGWorkflowAutoTransitionExecution setWorkflowTransition(AGWorkflowTransition value) {

		return setValue(WORKFLOW_TRANSITION, value);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGWorkflowAutoTransitionExecution setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowAutoTransitionExecutionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowItem m_workflowItem;
	private AGWorkflowTransition m_workflowTransition;
	private AGTransaction m_transaction;
}

