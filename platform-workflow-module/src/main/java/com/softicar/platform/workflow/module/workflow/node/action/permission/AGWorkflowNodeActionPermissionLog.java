package com.softicar.platform.workflow.module.workflow.node.action.permission;

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
 * This is the automatically generated class AGWorkflowNodeActionPermissionLog for
 * database table <i>Workflow.WorkflowNodeActionPermissionLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowNodeActionPermissionLog extends AbstractDbRecord<AGWorkflowNodeActionPermissionLog, Tuple2<AGWorkflowNodeActionPermission, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowNodeActionPermissionLog, AGWorkflowNodeActionPermissionLog, Tuple2<AGWorkflowNodeActionPermission, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowNodeActionPermissionLog", AGWorkflowNodeActionPermissionLog::new, AGWorkflowNodeActionPermissionLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_PERMISSION_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_PERMISSION_LOGS);
	}

	public static final IDbForeignField<AGWorkflowNodeActionPermissionLog, AGWorkflowNodeActionPermission> WORKFLOW_NODE_ACTION_PERMISSION = BUILDER.addForeignField("workflowNodeActionPermission", o->o.m_workflowNodeActionPermission, (o,v)->o.m_workflowNodeActionPermission=v, AGWorkflowNodeActionPermission.ID).setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION_PERMISSION);
	public static final IDbForeignField<AGWorkflowNodeActionPermissionLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbBooleanField<AGWorkflowNodeActionPermissionLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowNodeActionPermissionLog, AGUuid> PERMISSION_UUID = BUILDER.addForeignField("permissionUuid", o->o.m_permissionUuid, (o,v)->o.m_permissionUuid=v, AGUuid.ID).setTitle(WorkflowI18n.PERMISSION_UUID).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowNodeActionPermissionLog, Tuple2<AGWorkflowNodeActionPermission, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_NODE_ACTION_PERMISSION, TRANSACTION));
	public static final DbRecordTable<AGWorkflowNodeActionPermissionLog, Tuple2<AGWorkflowNodeActionPermission, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowNodeActionPermissionLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowNodeActionPermissionID() {

		return getValueId(WORKFLOW_NODE_ACTION_PERMISSION);
	}

	public final AGWorkflowNodeActionPermission getWorkflowNodeActionPermission() {

		return getValue(WORKFLOW_NODE_ACTION_PERMISSION);
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

	public final AGWorkflowNodeActionPermissionLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getPermissionUuidID() {

		return getValueId(PERMISSION_UUID);
	}

	public final AGUuid getPermissionUuid() {

		return getValue(PERMISSION_UUID);
	}

	public final AGWorkflowNodeActionPermissionLog setPermissionUuid(AGUuid value) {

		return setValue(PERMISSION_UUID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowNodeActionPermissionLog, Tuple2<AGWorkflowNodeActionPermission, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowNodeActionPermission m_workflowNodeActionPermission;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGUuid m_permissionUuid;
}

