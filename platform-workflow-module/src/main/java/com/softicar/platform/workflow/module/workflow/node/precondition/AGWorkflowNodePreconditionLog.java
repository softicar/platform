package com.softicar.platform.workflow.module.workflow.node.precondition;

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
 * This is the automatically generated class AGWorkflowNodePreconditionLog for
 * database table <i>Workflow.WorkflowNodePreconditionLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowNodePreconditionLog extends AbstractDbRecord<AGWorkflowNodePreconditionLog, Tuple2<AGWorkflowNodePrecondition, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowNodePreconditionLog, AGWorkflowNodePreconditionLog, Tuple2<AGWorkflowNodePrecondition, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowNodePreconditionLog", AGWorkflowNodePreconditionLog::new, AGWorkflowNodePreconditionLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_NODE_PRECONDITION_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_NODE_PRECONDITION_LOGS);
	}

	public static final IDbForeignField<AGWorkflowNodePreconditionLog, AGWorkflowNodePrecondition> WORKFLOW_NODE_PRECONDITION = BUILDER.addForeignField("workflowNodePrecondition", o->o.m_workflowNodePrecondition, (o,v)->o.m_workflowNodePrecondition=v, AGWorkflowNodePrecondition.ID).setTitle(WorkflowI18n.WORKFLOW_NODE_PRECONDITION).setCascade(true, true).setForeignKeyName("WorkflowNodePreconditionLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowNodePreconditionLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("WorkflowNodePreconditionLog_ibfk_2");
	public static final IDbBooleanField<AGWorkflowNodePreconditionLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowNodePreconditionLog, AGUuid> FUNCTION = BUILDER.addForeignField("function", o->o.m_function, (o,v)->o.m_function=v, AGUuid.ID).setTitle(WorkflowI18n.FUNCTION).setNullable().setDefault(null).setForeignKeyName("WorkflowNodePreconditionLog_ibfk_3");
	public static final IDbTableKey<AGWorkflowNodePreconditionLog, Tuple2<AGWorkflowNodePrecondition, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_NODE_PRECONDITION, TRANSACTION));
	public static final IDbKey<AGWorkflowNodePreconditionLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGWorkflowNodePreconditionLog> IK_FUNCTION = BUILDER.addIndexKey("function", FUNCTION);
	public static final DbRecordTable<AGWorkflowNodePreconditionLog, Tuple2<AGWorkflowNodePrecondition, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowNodePreconditionLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowNodePreconditionID() {

		return getValueId(WORKFLOW_NODE_PRECONDITION);
	}

	public final AGWorkflowNodePrecondition getWorkflowNodePrecondition() {

		return getValue(WORKFLOW_NODE_PRECONDITION);
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

	public final AGWorkflowNodePreconditionLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getFunctionID() {

		return getValueId(FUNCTION);
	}

	public final AGUuid getFunction() {

		return getValue(FUNCTION);
	}

	public final AGWorkflowNodePreconditionLog setFunction(AGUuid value) {

		return setValue(FUNCTION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowNodePreconditionLog, Tuple2<AGWorkflowNodePrecondition, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowNodePrecondition m_workflowNodePrecondition;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGUuid m_function;
}

