package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

/**
 * This is the automatically generated class AGWorkflowTask for
 * database table <i>Workflow.WorkflowTask</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTaskGenerated extends AbstractDbObject<AGWorkflowTask> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowTask, AGWorkflowTaskGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowTask", AGWorkflowTask::new, AGWorkflowTask.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TASK);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TASKS);
	}

	public static final IDbIdField<AGWorkflowTask> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowTask, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbForeignField<AGWorkflowTask, AGWorkflowItem> WORKFLOW_ITEM = BUILDER.addForeignField("workflowItem", o->o.m_workflowItem, (o,v)->o.m_workflowItem=v, AGWorkflowItem.ID).setTitle(WorkflowI18n.WORKFLOW_ITEM);
	public static final IDbForeignField<AGWorkflowTask, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(WorkflowI18n.USER);
	public static final IDbBooleanField<AGWorkflowTask> CLOSED = BUILDER.addBooleanField("closed", o->o.m_closed, (o,v)->o.m_closed=v).setTitle(WorkflowI18n.CLOSED).setDefault(false);
	public static final IDbBooleanField<AGWorkflowTask> NOTIFY = BUILDER.addBooleanField("notify", o->o.m_notify, (o,v)->o.m_notify=v).setTitle(WorkflowI18n.NOTIFY).setDefault(false);
	public static final AGWorkflowTaskTable TABLE = new AGWorkflowTaskTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowTask> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowTask get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGWorkflowTask setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final Integer getWorkflowItemID() {

		return getValueId(WORKFLOW_ITEM);
	}

	public final AGWorkflowItem getWorkflowItem() {

		return getValue(WORKFLOW_ITEM);
	}

	public final AGWorkflowTask setWorkflowItem(AGWorkflowItem value) {

		return setValue(WORKFLOW_ITEM, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGWorkflowTask setUser(AGUser value) {

		return setValue(USER, value);
	}

	public final Boolean isClosed() {

		return getValue(CLOSED);
	}

	public final AGWorkflowTask setClosed(Boolean value) {

		return setValue(CLOSED, value);
	}

	public final Boolean isNotify() {

		return getValue(NOTIFY);
	}

	public final AGWorkflowTask setNotify(Boolean value) {

		return setValue(NOTIFY, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowTaskTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private AGWorkflowItem m_workflowItem;
	private AGUser m_user;
	private Boolean m_closed;
	private Boolean m_notify;
}

