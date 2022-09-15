package com.softicar.platform.workflow.module.workflow.transition.permission;

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
 * This is the automatically generated class AGWorkflowTransitionPermissionLog for
 * database table <i>Workflow.WorkflowTransitionPermissionLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTransitionPermissionLog extends AbstractDbRecord<AGWorkflowTransitionPermissionLog, Tuple2<AGWorkflowTransitionPermission, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowTransitionPermissionLog, AGWorkflowTransitionPermissionLog, Tuple2<AGWorkflowTransitionPermission, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowTransitionPermissionLog", AGWorkflowTransitionPermissionLog::new, AGWorkflowTransitionPermissionLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TRANSITION_PERMISSION_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TRANSITION_PERMISSION_LOGS);
	}

	public static final IDbForeignField<AGWorkflowTransitionPermissionLog, AGWorkflowTransitionPermission> TRANSITION_PERMISSION = BUILDER.addForeignField("transitionPermission", o->o.m_transitionPermission, (o,v)->o.m_transitionPermission=v, AGWorkflowTransitionPermission.ID).setTitle(WorkflowI18n.TRANSITION_PERMISSION).setCascade(true, true).setForeignKeyName("WorkflowTransitionPermissionLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowTransitionPermissionLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("WorkflowTransitionPermissionLog_ibfk_2");
	public static final IDbForeignField<AGWorkflowTransitionPermissionLog, AGUuid> PERMISSION = BUILDER.addForeignField("permission", o->o.m_permission, (o,v)->o.m_permission=v, AGUuid.ID).setTitle(WorkflowI18n.PERMISSION).setNullable().setDefault(null).setForeignKeyName("WorkflowTransitionPermissionLog_ibfk_3");
	public static final IDbBooleanField<AGWorkflowTransitionPermissionLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowTransitionPermissionLog, Tuple2<AGWorkflowTransitionPermission, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(TRANSITION_PERMISSION, TRANSACTION));
	public static final IDbKey<AGWorkflowTransitionPermissionLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGWorkflowTransitionPermissionLog> IK_PERMISSION = BUILDER.addIndexKey("permission", PERMISSION);
	public static final DbRecordTable<AGWorkflowTransitionPermissionLog, Tuple2<AGWorkflowTransitionPermission, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowTransitionPermissionLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransitionPermissionID() {

		return getValueId(TRANSITION_PERMISSION);
	}

	public final AGWorkflowTransitionPermission getTransitionPermission() {

		return getValue(TRANSITION_PERMISSION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Integer getPermissionID() {

		return getValueId(PERMISSION);
	}

	public final AGUuid getPermission() {

		return getValue(PERMISSION);
	}

	public final AGWorkflowTransitionPermissionLog setPermission(AGUuid value) {

		return setValue(PERMISSION, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowTransitionPermissionLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowTransitionPermissionLog, Tuple2<AGWorkflowTransitionPermission, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowTransitionPermission m_transitionPermission;
	private AGTransaction m_transaction;
	private AGUuid m_permission;
	private Boolean m_active;
}

