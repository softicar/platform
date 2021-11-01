package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowTaskLog for
 * database table <i>Workflow.WorkflowTaskLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTaskLog extends AbstractDbRecord<AGWorkflowTaskLog, Tuple2<AGWorkflowTask, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowTaskLog, AGWorkflowTaskLog, Tuple2<AGWorkflowTask, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowTaskLog", AGWorkflowTaskLog::new, AGWorkflowTaskLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TASK_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TASK_LOGS);
	}

	public static final IDbForeignField<AGWorkflowTaskLog, AGWorkflowTask> WORKFLOW_TASK = BUILDER.addForeignField("workflowTask", o->o.m_workflowTask, (o,v)->o.m_workflowTask=v, AGWorkflowTask.ID).setTitle(WorkflowI18n.WORKFLOW_TASK);
	public static final IDbForeignField<AGWorkflowTaskLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbForeignField<AGWorkflowTaskLog, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(WorkflowI18n.USER).setNullable().setDefault(null);
	public static final IDbBooleanField<AGWorkflowTaskLog> CLOSED = BUILDER.addBooleanField("closed", o->o.m_closed, (o,v)->o.m_closed=v).setTitle(WorkflowI18n.CLOSED).setNullable().setDefault(null);
	public static final IDbBooleanField<AGWorkflowTaskLog> NOTIFY = BUILDER.addBooleanField("notify", o->o.m_notify, (o,v)->o.m_notify=v).setTitle(WorkflowI18n.NOTIFY).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowTaskLog, Tuple2<AGWorkflowTask, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_TASK, TRANSACTION));
	public static final DbRecordTable<AGWorkflowTaskLog, Tuple2<AGWorkflowTask, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowTaskLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowTaskID() {

		return getValueId(WORKFLOW_TASK);
	}

	public final AGWorkflowTask getWorkflowTask() {

		return getValue(WORKFLOW_TASK);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGWorkflowTaskLog setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Boolean isClosed() {

		return getValue(CLOSED);
	}

	public final AGWorkflowTaskLog setClosed(Boolean value) {

		return setValue(CLOSED, value);
	}

	public final Boolean isNotify() {

		return getValue(NOTIFY);
	}

	public final AGWorkflowTaskLog setNotify(Boolean value) {

		return setValue(NOTIFY, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowTaskLog, Tuple2<AGWorkflowTask, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowTask m_workflowTask;
	private AGTransaction m_transaction;
	private AGUser m_user;
	private Boolean m_closed;
	private Boolean m_notify;
}

