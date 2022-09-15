package com.softicar.platform.workflow.module.workflow.node;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowNodeLog for
 * database table <i>Workflow.WorkflowNodeLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowNodeLog extends AbstractDbRecord<AGWorkflowNodeLog, Tuple2<AGWorkflowNode, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowNodeLog, AGWorkflowNodeLog, Tuple2<AGWorkflowNode, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowNodeLog", AGWorkflowNodeLog::new, AGWorkflowNodeLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_NODE_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_NODE_LOGS);
	}

	public static final IDbForeignField<AGWorkflowNodeLog, AGWorkflowNode> WORKFLOW_NODE = BUILDER.addForeignField("workflowNode", o->o.m_workflowNode, (o,v)->o.m_workflowNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.WORKFLOW_NODE).setCascade(true, true).setForeignKeyName("WorkflowNodeLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowNodeLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setCascade(true, true).setForeignKeyName("WorkflowNodeLog_ibfk_2");
	public static final IDbStringField<AGWorkflowNodeLog> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbIntegerField<AGWorkflowNodeLog> X_COORDINATE = BUILDER.addIntegerField("XCoordinate", o->o.m_xCoordinate, (o,v)->o.m_xCoordinate=v).setTitle(WorkflowI18n.X_COORDINATE).setNullable().setDefault(null);
	public static final IDbIntegerField<AGWorkflowNodeLog> Y_COORDINATE = BUILDER.addIntegerField("YCoordinate", o->o.m_yCoordinate, (o,v)->o.m_yCoordinate=v).setTitle(WorkflowI18n.Y_COORDINATE).setNullable().setDefault(null);
	public static final IDbBooleanField<AGWorkflowNodeLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowNodeLog, Tuple2<AGWorkflowNode, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_NODE, TRANSACTION));
	public static final IDbKey<AGWorkflowNodeLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final DbRecordTable<AGWorkflowNodeLog, Tuple2<AGWorkflowNode, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowNodeLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowNodeID() {

		return getValueId(WORKFLOW_NODE);
	}

	public final AGWorkflowNode getWorkflowNode() {

		return getValue(WORKFLOW_NODE);
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

	public final AGWorkflowNodeLog setName(String value) {

		return setValue(NAME, value);
	}

	public final Integer getXCoordinate() {

		return getValue(X_COORDINATE);
	}

	public final AGWorkflowNodeLog setXCoordinate(Integer value) {

		return setValue(X_COORDINATE, value);
	}

	public final Integer getYCoordinate() {

		return getValue(Y_COORDINATE);
	}

	public final AGWorkflowNodeLog setYCoordinate(Integer value) {

		return setValue(Y_COORDINATE, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowNodeLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowNodeLog, Tuple2<AGWorkflowNode, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowNode m_workflowNode;
	private AGTransaction m_transaction;
	private String m_name;
	private Integer m_xCoordinate;
	private Integer m_yCoordinate;
	private Boolean m_active;
}

