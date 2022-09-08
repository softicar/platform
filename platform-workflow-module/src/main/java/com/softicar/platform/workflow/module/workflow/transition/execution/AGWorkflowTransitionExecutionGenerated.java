package com.softicar.platform.workflow.module.workflow.transition.execution;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;

/**
 * This is the automatically generated class AGWorkflowTransitionExecution for
 * database table <i>Workflow.WorkflowTransitionExecution</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTransitionExecutionGenerated extends AbstractDbObject<AGWorkflowTransitionExecution> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowTransitionExecution, AGWorkflowTransitionExecutionGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowTransitionExecution", AGWorkflowTransitionExecution::new, AGWorkflowTransitionExecution.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TRANSITION_EXECUTION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TRANSITION_EXECUTIONS);
	}

	public static final IDbIdField<AGWorkflowTransitionExecution> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowTransitionExecution, AGWorkflowTask> WORKFLOW_TASK = BUILDER.addForeignField("workflowTask", o->o.m_workflowTask, (o,v)->o.m_workflowTask=v, AGWorkflowTask.ID).setTitle(WorkflowI18n.WORKFLOW_TASK).setForeignKeyName("WorkflowTransitionExecution_ibfk_1");
	public static final IDbForeignField<AGWorkflowTransitionExecution, AGWorkflowTransition> WORKFLOW_TRANSITION = BUILDER.addForeignField("workflowTransition", o->o.m_workflowTransition, (o,v)->o.m_workflowTransition=v, AGWorkflowTransition.ID).setTitle(WorkflowI18n.WORKFLOW_TRANSITION).setForeignKeyName("WorkflowTransitionExecution_ibfk_2");
	public static final IDbForeignField<AGWorkflowTransitionExecution, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setForeignKeyName("WorkflowTransitionExecution_ibfk_3");
	public static final IDbKey<AGWorkflowTransitionExecution> UK_WORKFLOW_TASK = BUILDER.addUniqueKey("workflowTask", WORKFLOW_TASK);
	public static final IDbKey<AGWorkflowTransitionExecution> IK_WORKFLOW_TRANSITION = BUILDER.addIndexKey("workflowTransition", WORKFLOW_TRANSITION);
	public static final IDbKey<AGWorkflowTransitionExecution> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final AGWorkflowTransitionExecutionTable TABLE = new AGWorkflowTransitionExecutionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowTransitionExecution> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowTransitionExecution get(Integer id) {

		return TABLE.get(id);
	}

	public static AGWorkflowTransitionExecution loadByWorkflowTask(AGWorkflowTask workflowTask) {

		return TABLE//
				.createSelect()
				.where(WORKFLOW_TASK.equal(workflowTask))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowTaskID() {

		return getValueId(WORKFLOW_TASK);
	}

	public final AGWorkflowTask getWorkflowTask() {

		return getValue(WORKFLOW_TASK);
	}

	public final AGWorkflowTransitionExecution setWorkflowTask(AGWorkflowTask value) {

		return setValue(WORKFLOW_TASK, value);
	}

	public final Integer getWorkflowTransitionID() {

		return getValueId(WORKFLOW_TRANSITION);
	}

	public final AGWorkflowTransition getWorkflowTransition() {

		return getValue(WORKFLOW_TRANSITION);
	}

	public final AGWorkflowTransitionExecution setWorkflowTransition(AGWorkflowTransition value) {

		return setValue(WORKFLOW_TRANSITION, value);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGWorkflowTransitionExecution setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowTransitionExecutionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowTask m_workflowTask;
	private AGWorkflowTransition m_workflowTransition;
	private AGTransaction m_transaction;
}

