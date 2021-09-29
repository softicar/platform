package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

/**
 * This is the automatically generated class AGWorkflowItemMessage for
 * database table <i>Workflow.WorkflowItemMessage</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowItemMessageGenerated extends AbstractDbObject<AGWorkflowItemMessage> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowItemMessage, AGWorkflowItemMessageGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowItemMessage", AGWorkflowItemMessage::new, AGWorkflowItemMessage.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_ITEM_MESSAGE);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_ITEM_MESSAGES);
	}

	public static final IDbIdField<AGWorkflowItemMessage> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowItemMessage, AGWorkflowItem> WORKFLOW_ITEM = BUILDER.addForeignField("workflowItem", o->o.m_workflowItem, (o,v)->o.m_workflowItem=v, AGWorkflowItem.ID).setTitle(WorkflowI18n.WORKFLOW_ITEM);
	public static final IDbForeignField<AGWorkflowItemMessage, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbStringField<AGWorkflowItemMessage> TEXT = BUILDER.addStringField("text", o->o.m_text, (o,v)->o.m_text=v).setTitle(WorkflowI18n.TEXT).setLengthBits(16);
	public static final AGWorkflowItemMessageTable TABLE = new AGWorkflowItemMessageTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowItemMessage> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowItemMessage get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowItemID() {

		return getValueId(WORKFLOW_ITEM);
	}

	public final AGWorkflowItem getWorkflowItem() {

		return getValue(WORKFLOW_ITEM);
	}

	public final AGWorkflowItemMessage setWorkflowItem(AGWorkflowItem value) {

		return setValue(WORKFLOW_ITEM, value);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGWorkflowItemMessage setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final String getText() {

		return getValue(TEXT);
	}

	public final AGWorkflowItemMessage setText(String value) {

		return setValue(TEXT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowItemMessageTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowItem m_workflowItem;
	private AGTransaction m_transaction;
	private String m_text;
}

