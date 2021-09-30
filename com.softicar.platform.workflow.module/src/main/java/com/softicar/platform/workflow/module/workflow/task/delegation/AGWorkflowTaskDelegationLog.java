package com.softicar.platform.workflow.module.workflow.task.delegation;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;

/**
 * This is the automatically generated class AGWorkflowTaskDelegationLog for
 * database table <i>Workflow.WorkflowTaskDelegationLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTaskDelegationLog extends AbstractDbRecord<AGWorkflowTaskDelegationLog, Tuple2<AGWorkflowTaskDelegation, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowTaskDelegationLog, AGWorkflowTaskDelegationLog, Tuple2<AGWorkflowTaskDelegation, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowTaskDelegationLog", AGWorkflowTaskDelegationLog::new, AGWorkflowTaskDelegationLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TASK_DELEGATION_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TASK_DELEGATION_LOGS);
	}

	public static final IDbForeignRowField<AGWorkflowTaskDelegationLog, AGWorkflowTaskDelegation, AGWorkflowTask> WORKFLOW_TASK_DELEGATION = BUILDER.addForeignRowField("workflowTaskDelegation", o->o.m_workflowTaskDelegation, (o,v)->o.m_workflowTaskDelegation=v, AGWorkflowTaskDelegation.WORKFLOW_TASK).setTitle(WorkflowI18n.WORKFLOW_TASK_DELEGATION);
	public static final IDbForeignField<AGWorkflowTaskDelegationLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbForeignField<AGWorkflowTaskDelegationLog, AGUser> TARGET_USER = BUILDER.addForeignField("targetUser", o->o.m_targetUser, (o,v)->o.m_targetUser=v, AGUser.ID).setTitle(WorkflowI18n.TARGET_USER).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowTaskDelegationLog, AGUser> DELEGATED_BY = BUILDER.addForeignField("delegatedBy", o->o.m_delegatedBy, (o,v)->o.m_delegatedBy=v, AGUser.ID).setTitle(WorkflowI18n.DELEGATED_BY).setNullable().setDefault(null);
	public static final IDbBooleanField<AGWorkflowTaskDelegationLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowTaskDelegationLog, Tuple2<AGWorkflowTaskDelegation, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_TASK_DELEGATION, TRANSACTION));
	public static final DbRecordTable<AGWorkflowTaskDelegationLog, Tuple2<AGWorkflowTaskDelegation, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowTaskDelegationLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGWorkflowTaskDelegation getWorkflowTaskDelegation() {

		return getValue(WORKFLOW_TASK_DELEGATION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Integer getTargetUserID() {

		return getValueId(TARGET_USER);
	}

	public final AGUser getTargetUser() {

		return getValue(TARGET_USER);
	}

	public final AGWorkflowTaskDelegationLog setTargetUser(AGUser value) {

		return setValue(TARGET_USER, value);
	}

	public final Integer getDelegatedByID() {

		return getValueId(DELEGATED_BY);
	}

	public final AGUser getDelegatedBy() {

		return getValue(DELEGATED_BY);
	}

	public final AGWorkflowTaskDelegationLog setDelegatedBy(AGUser value) {

		return setValue(DELEGATED_BY, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowTaskDelegationLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowTaskDelegationLog, Tuple2<AGWorkflowTaskDelegation, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowTaskDelegation m_workflowTaskDelegation;
	private AGTransaction m_transaction;
	private AGUser m_targetUser;
	private AGUser m_delegatedBy;
	private Boolean m_active;
}

