package com.softicar.platform.workflow.module.demo.approver;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowDemoObjectApproverLog for
 * database table <i>Workflow.WorkflowDemoObjectApproverLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowDemoObjectApproverLog extends AbstractDbRecord<AGWorkflowDemoObjectApproverLog, Tuple2<AGWorkflowDemoObjectApprover, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowDemoObjectApproverLog, AGWorkflowDemoObjectApproverLog, Tuple2<AGWorkflowDemoObjectApprover, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowDemoObjectApproverLog", AGWorkflowDemoObjectApproverLog::new, AGWorkflowDemoObjectApproverLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_DEMO_OBJECT_APPROVER_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_DEMO_OBJECT_APPROVER_LOGS);
	}

	public static final IDbForeignField<AGWorkflowDemoObjectApproverLog, AGWorkflowDemoObjectApprover> APPROVER = BUILDER.addForeignField("approver", o->o.m_approver, (o,v)->o.m_approver=v, AGWorkflowDemoObjectApprover.ID).setTitle(WorkflowI18n.APPROVER);
	public static final IDbForeignField<AGWorkflowDemoObjectApproverLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbIntegerField<AGWorkflowDemoObjectApproverLog> APPROVAL_TIER = BUILDER.addIntegerField("approvalTier", o->o.m_approvalTier, (o,v)->o.m_approvalTier=v).setTitle(WorkflowI18n.APPROVAL_TIER).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowDemoObjectApproverLog, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(WorkflowI18n.USER).setNullable().setDefault(null);
	public static final IDbBooleanField<AGWorkflowDemoObjectApproverLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbBooleanField<AGWorkflowDemoObjectApproverLog> APPROVED = BUILDER.addBooleanField("approved", o->o.m_approved, (o,v)->o.m_approved=v).setTitle(WorkflowI18n.APPROVED).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowDemoObjectApproverLog, Tuple2<AGWorkflowDemoObjectApprover, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(APPROVER, TRANSACTION));
	public static final DbRecordTable<AGWorkflowDemoObjectApproverLog, Tuple2<AGWorkflowDemoObjectApprover, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowDemoObjectApproverLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getApproverID() {

		return getValueId(APPROVER);
	}

	public final AGWorkflowDemoObjectApprover getApprover() {

		return getValue(APPROVER);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Integer getApprovalTier() {

		return getValue(APPROVAL_TIER);
	}

	public final AGWorkflowDemoObjectApproverLog setApprovalTier(Integer value) {

		return setValue(APPROVAL_TIER, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGWorkflowDemoObjectApproverLog setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowDemoObjectApproverLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Boolean isApproved() {

		return getValue(APPROVED);
	}

	public final AGWorkflowDemoObjectApproverLog setApproved(Boolean value) {

		return setValue(APPROVED, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowDemoObjectApproverLog, Tuple2<AGWorkflowDemoObjectApprover, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowDemoObjectApprover m_approver;
	private AGTransaction m_transaction;
	private Integer m_approvalTier;
	private AGUser m_user;
	private Boolean m_active;
	private Boolean m_approved;
}

