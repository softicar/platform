package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

/**
 * This is the automatically generated class AGWorkflowVersionLog for
 * database table <i>Workflow.WorkflowVersionLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowVersionLog extends AbstractDbRecord<AGWorkflowVersionLog, Tuple2<AGWorkflowVersion, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowVersionLog, AGWorkflowVersionLog, Tuple2<AGWorkflowVersion, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowVersionLog", AGWorkflowVersionLog::new, AGWorkflowVersionLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_VERSION_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_VERSION_LOGS);
	}

	public static final IDbForeignField<AGWorkflowVersionLog, AGWorkflowVersion> WORKFLOW_VERSION = BUILDER.addForeignField("workflowVersion", o->o.m_workflowVersion, (o,v)->o.m_workflowVersion=v, AGWorkflowVersion.ID).setTitle(WorkflowI18n.WORKFLOW_VERSION).setForeignKeyName("WorkflowVersionLog_ibfk_1");
	public static final IDbForeignField<AGWorkflowVersionLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION).setForeignKeyName("WorkflowVersionLog_ibfk_2");
	public static final IDbForeignField<AGWorkflowVersionLog, AGWorkflowNode> ROOT_NODE = BUILDER.addForeignField("rootNode", o->o.m_rootNode, (o,v)->o.m_rootNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.ROOT_NODE).setNullable().setDefault(null).setForeignKeyName("WorkflowVersionLog_ibfk_3");
	public static final IDbBooleanField<AGWorkflowVersionLog> DRAFT = BUILDER.addBooleanField("draft", o->o.m_draft, (o,v)->o.m_draft=v).setTitle(WorkflowI18n.DRAFT).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowVersionLog, Tuple2<AGWorkflowVersion, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_VERSION, TRANSACTION));
	public static final IDbKey<AGWorkflowVersionLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGWorkflowVersionLog> IK_ROOT_NODE = BUILDER.addIndexKey("rootNode", ROOT_NODE);
	public static final DbRecordTable<AGWorkflowVersionLog, Tuple2<AGWorkflowVersion, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowVersionLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowVersionID() {

		return getValueId(WORKFLOW_VERSION);
	}

	public final AGWorkflowVersion getWorkflowVersion() {

		return getValue(WORKFLOW_VERSION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Integer getRootNodeID() {

		return getValueId(ROOT_NODE);
	}

	public final AGWorkflowNode getRootNode() {

		return getValue(ROOT_NODE);
	}

	public final AGWorkflowVersionLog setRootNode(AGWorkflowNode value) {

		return setValue(ROOT_NODE, value);
	}

	public final Boolean isDraft() {

		return getValue(DRAFT);
	}

	public final AGWorkflowVersionLog setDraft(Boolean value) {

		return setValue(DRAFT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowVersionLog, Tuple2<AGWorkflowVersion, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowVersion m_workflowVersion;
	private AGTransaction m_transaction;
	private AGWorkflowNode m_rootNode;
	private Boolean m_draft;
}

