package com.softicar.platform.workflow.module.workflow.node.action.role;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowNodeActionRoleLog for
 * database table <i>Workflow.WorkflowNodeActionRoleLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowNodeActionRoleLog extends AbstractDbRecord<AGWorkflowNodeActionRoleLog, Tuple2<AGWorkflowNodeActionRole, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowNodeActionRoleLog, AGWorkflowNodeActionRoleLog, Tuple2<AGWorkflowNodeActionRole, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowNodeActionRoleLog", AGWorkflowNodeActionRoleLog::new, AGWorkflowNodeActionRoleLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_ROLE_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_ROLE_LOGS);
	}

	public static final IDbForeignField<AGWorkflowNodeActionRoleLog, AGWorkflowNodeActionRole> WORKFLOW_NODE_ACTION_ROLE = BUILDER.addForeignField("workflowNodeActionRole", o->o.m_workflowNodeActionRole, (o,v)->o.m_workflowNodeActionRole=v, AGWorkflowNodeActionRole.ID).setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_ROLE);
	public static final IDbForeignField<AGWorkflowNodeActionRoleLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbBooleanField<AGWorkflowNodeActionRoleLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowNodeActionRoleLog, AGUuid> ROLE_UUID = BUILDER.addForeignField("roleUuid", o->o.m_roleUuid, (o,v)->o.m_roleUuid=v, AGUuid.ID).setTitle(WorkflowI18n.ROLE_UUID).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowNodeActionRoleLog, Tuple2<AGWorkflowNodeActionRole, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_NODE_ACTION_ROLE, TRANSACTION));
	public static final DbRecordTable<AGWorkflowNodeActionRoleLog, Tuple2<AGWorkflowNodeActionRole, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowNodeActionRoleLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowNodeActionRoleID() {

		return getValueId(WORKFLOW_NODE_ACTION_ROLE);
	}

	public final AGWorkflowNodeActionRole getWorkflowNodeActionRole() {

		return getValue(WORKFLOW_NODE_ACTION_ROLE);
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

	public final AGWorkflowNodeActionRoleLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getRoleUuidID() {

		return getValueId(ROLE_UUID);
	}

	public final AGUuid getRoleUuid() {

		return getValue(ROLE_UUID);
	}

	public final AGWorkflowNodeActionRoleLog setRoleUuid(AGUuid value) {

		return setValue(ROLE_UUID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowNodeActionRoleLog, Tuple2<AGWorkflowNodeActionRole, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowNodeActionRole m_workflowNodeActionRole;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGUuid m_roleUuid;
}

