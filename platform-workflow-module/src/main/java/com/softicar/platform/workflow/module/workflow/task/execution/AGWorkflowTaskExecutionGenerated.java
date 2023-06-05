package com.softicar.platform.workflow.module.workflow.task.execution;

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
 * This is the automatically generated class AGWorkflowTaskExecution for
 * database table <i>Workflow.WorkflowTaskExecution</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTaskExecutionGenerated extends AbstractDbObject<AGWorkflowTaskExecution> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowTaskExecution, AGWorkflowTaskExecutionGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowTaskExecution", AGWorkflowTaskExecution::new, AGWorkflowTaskExecution.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TASK_EXECUTION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TASK_EXECUTIONS);
	}

	public static final IDbIdField<AGWorkflowTaskExecution> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowTaskExecution, AGWorkflowTask> WORKFLOW_TASK = BUILDER.addForeignField("workflowTask", o->o.m_workflowTask, (o,v)->o.m_workflowTask=v, AGWorkflowTask.ID).setTitle(WorkflowI18n.WORKFLOW_TASK).setForeignKeyName("WorkflowTaskExecution_ibfk_1");
	public static final IDbForeignField<AGWorkflowTaskExecution, AGWorkflowTransition> WORKFLOW_TRANSITION = BUILDER.addForeignField("workflowTransition", o->o.m_workflowTransition, (o,v)->o.m_workflowTransition=v, AGWorkflowTransition.ID).setTitle(WorkflowI18n.WORKFLOW_TRANSITION).setForeignKeyName("WorkflowTaskExecution_ibfk_2");
	public static final IDbForeignField<AGWorkflowTaskExecution, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setForeignKeyName("WorkflowTaskExecution_ibfk_3");
	public static final IDbKey<AGWorkflowTaskExecution> UK_WORKFLOW_TASK = BUILDER.addUniqueKey("workflowTask", WORKFLOW_TASK);
	public static final IDbKey<AGWorkflowTaskExecution> IK_WORKFLOW_TRANSITION = BUILDER.addIndexKey("workflowTransition", WORKFLOW_TRANSITION);
	public static final IDbKey<AGWorkflowTaskExecution> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final AGWorkflowTaskExecutionTable TABLE = new AGWorkflowTaskExecutionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowTaskExecution> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowTaskExecution get(Integer id) {

		return TABLE.get(id);
	}

	public static AGWorkflowTaskExecution loadByWorkflowTask(AGWorkflowTask workflowTask) {

		return TABLE//
				.createSelect()
				.where(WORKFLOW_TASK.isEqual(workflowTask))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowTaskID() {

		return getValueId(WORKFLOW_TASK);
	}

	public final AGWorkflowTask getWorkflowTask() {

		return getValue(WORKFLOW_TASK);
	}

	public final AGWorkflowTaskExecution setWorkflowTask(AGWorkflowTask value) {

		return setValue(WORKFLOW_TASK, value);
	}

	public final Integer getWorkflowTransitionID() {

		return getValueId(WORKFLOW_TRANSITION);
	}

	public final AGWorkflowTransition getWorkflowTransition() {

		return getValue(WORKFLOW_TRANSITION);
	}

	public final AGWorkflowTaskExecution setWorkflowTransition(AGWorkflowTransition value) {

		return setValue(WORKFLOW_TRANSITION, value);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGWorkflowTaskExecution setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowTaskExecutionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowTask m_workflowTask;
	private AGWorkflowTransition m_workflowTransition;
	private AGTransaction m_transaction;
}

