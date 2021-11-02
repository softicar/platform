package com.softicar.platform.workflow.module.workflow.transition.role;

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
 * This is the automatically generated class AGWorkflowTransitionRoleLog for
 * database table <i>Workflow.WorkflowTransitionRoleLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTransitionRoleLog extends AbstractDbRecord<AGWorkflowTransitionRoleLog, Tuple2<AGWorkflowTransitionRole, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowTransitionRoleLog, AGWorkflowTransitionRoleLog, Tuple2<AGWorkflowTransitionRole, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowTransitionRoleLog", AGWorkflowTransitionRoleLog::new, AGWorkflowTransitionRoleLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TRANSITION_ROLE_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TRANSITION_ROLE_LOGS);
	}

	public static final IDbForeignField<AGWorkflowTransitionRoleLog, AGWorkflowTransitionRole> TRANSITION_ROLE = BUILDER.addForeignField("transitionRole", o->o.m_transitionRole, (o,v)->o.m_transitionRole=v, AGWorkflowTransitionRole.ID).setTitle(WorkflowI18n.TRANSITION_ROLE);
	public static final IDbForeignField<AGWorkflowTransitionRoleLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbForeignField<AGWorkflowTransitionRoleLog, AGUuid> ROLE = BUILDER.addForeignField("role", o->o.m_role, (o,v)->o.m_role=v, AGUuid.ID).setTitle(WorkflowI18n.ROLE).setNullable().setDefault(null);
	public static final IDbBooleanField<AGWorkflowTransitionRoleLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowTransitionRoleLog, Tuple2<AGWorkflowTransitionRole, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(TRANSITION_ROLE, TRANSACTION));
	public static final DbRecordTable<AGWorkflowTransitionRoleLog, Tuple2<AGWorkflowTransitionRole, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowTransitionRoleLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransitionRoleID() {

		return getValueId(TRANSITION_ROLE);
	}

	public final AGWorkflowTransitionRole getTransitionRole() {

		return getValue(TRANSITION_ROLE);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Integer getRoleID() {

		return getValueId(ROLE);
	}

	public final AGUuid getRole() {

		return getValue(ROLE);
	}

	public final AGWorkflowTransitionRoleLog setRole(AGUuid value) {

		return setValue(ROLE, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowTransitionRoleLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowTransitionRoleLog, Tuple2<AGWorkflowTransitionRole, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowTransitionRole m_transitionRole;
	private AGTransaction m_transaction;
	private AGUuid m_role;
	private Boolean m_active;
}

