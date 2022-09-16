package com.softicar.platform.workflow.module.demo;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

/**
 * This is the automatically generated class AGWorkflowDemoObjectLog for
 * database table <i>Workflow.WorkflowDemoObjectLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowDemoObjectLog extends AbstractDbRecord<AGWorkflowDemoObjectLog, Tuple2<AGWorkflowDemoObject, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowDemoObjectLog, AGWorkflowDemoObjectLog, Tuple2<AGWorkflowDemoObject, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowDemoObjectLog", AGWorkflowDemoObjectLog::new, AGWorkflowDemoObjectLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_DEMO_OBJECT_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_DEMO_OBJECT_LOGS);
	}

	public static final IDbForeignField<AGWorkflowDemoObjectLog, AGWorkflowDemoObject> WORKFLOW_DEMO_OBJECT = BUILDER.addForeignField("workflowDemoObject", o->o.m_workflowDemoObject, (o,v)->o.m_workflowDemoObject=v, AGWorkflowDemoObject.ID).setTitle(WorkflowI18n.WORKFLOW_DEMO_OBJECT).setCascade(true, true).setForeignKeyName("WorkflowDemoObjectLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowDemoObjectLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("WorkflowDemoObjectLog_ibfk_2");
	public static final IDbStringField<AGWorkflowDemoObjectLog> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME).setNullable().setDefault(null).setMaximumLength(50);
	public static final IDbBooleanField<AGWorkflowDemoObjectLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowDemoObjectLog, AGWorkflowItem> WORKFLOW_ITEM = BUILDER.addForeignField("workflowItem", o->o.m_workflowItem, (o,v)->o.m_workflowItem=v, AGWorkflowItem.ID).setTitle(WorkflowI18n.WORKFLOW_ITEM).setNullable().setDefault(null).setForeignKeyName("WorkflowDemoObjectLog_ibfk_3");
	public static final IDbTableKey<AGWorkflowDemoObjectLog, Tuple2<AGWorkflowDemoObject, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_DEMO_OBJECT, TRANSACTION));
	public static final IDbKey<AGWorkflowDemoObjectLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGWorkflowDemoObjectLog> IK_WORKFLOW_ITEM = BUILDER.addIndexKey("workflowItem", WORKFLOW_ITEM);
	public static final DbRecordTable<AGWorkflowDemoObjectLog, Tuple2<AGWorkflowDemoObject, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowDemoObjectLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowDemoObjectID() {

		return getValueId(WORKFLOW_DEMO_OBJECT);
	}

	public final AGWorkflowDemoObject getWorkflowDemoObject() {

		return getValue(WORKFLOW_DEMO_OBJECT);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWorkflowDemoObjectLog setName(String value) {

		return setValue(NAME, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowDemoObjectLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getWorkflowItemID() {

		return getValueId(WORKFLOW_ITEM);
	}

	public final AGWorkflowItem getWorkflowItem() {

		return getValue(WORKFLOW_ITEM);
	}

	public final AGWorkflowDemoObjectLog setWorkflowItem(AGWorkflowItem value) {

		return setValue(WORKFLOW_ITEM, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowDemoObjectLog, Tuple2<AGWorkflowDemoObject, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowDemoObject m_workflowDemoObject;
	private AGTransaction m_transaction;
	private String m_name;
	private Boolean m_active;
	private AGWorkflowItem m_workflowItem;
}

