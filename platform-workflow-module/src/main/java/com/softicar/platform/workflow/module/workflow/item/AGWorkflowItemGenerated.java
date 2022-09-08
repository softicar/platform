package com.softicar.platform.workflow.module.workflow.item;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

/**
 * This is the automatically generated class AGWorkflowItem for
 * database table <i>Workflow.WorkflowItem</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowItemGenerated extends AbstractDbObject<AGWorkflowItem> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowItem, AGWorkflowItemGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowItem", AGWorkflowItem::new, AGWorkflowItem.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_ITEM);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_ITEMS);
	}

	public static final IDbIdField<AGWorkflowItem> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowItem, AGWorkflow> WORKFLOW = BUILDER.addForeignField("workflow", o->o.m_workflow, (o,v)->o.m_workflow=v, AGWorkflow.ID).setTitle(WorkflowI18n.WORKFLOW).setForeignKeyName("WorkflowItem_ibfk_1");
	public static final IDbForeignField<AGWorkflowItem, AGWorkflowNode> WORKFLOW_NODE = BUILDER.addForeignField("workflowNode", o->o.m_workflowNode, (o,v)->o.m_workflowNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.WORKFLOW_NODE).setForeignKeyName("WorkflowItem_ibfk_2");
	public static final IDbKey<AGWorkflowItem> IK_WORKFLOW = BUILDER.addIndexKey("workflow", WORKFLOW);
	public static final IDbKey<AGWorkflowItem> IK_WORKFLOW_NODE = BUILDER.addIndexKey("workflowNode", WORKFLOW_NODE);
	public static final AGWorkflowItemTable TABLE = new AGWorkflowItemTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowItem> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowItem get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowID() {

		return getValueId(WORKFLOW);
	}

	public final AGWorkflow getWorkflow() {

		return getValue(WORKFLOW);
	}

	public final AGWorkflowItem setWorkflow(AGWorkflow value) {

		return setValue(WORKFLOW, value);
	}

	public final Integer getWorkflowNodeID() {

		return getValueId(WORKFLOW_NODE);
	}

	public final AGWorkflowNode getWorkflowNode() {

		return getValue(WORKFLOW_NODE);
	}

	public final AGWorkflowItem setWorkflowNode(AGWorkflowNode value) {

		return setValue(WORKFLOW_NODE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowItemTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflow m_workflow;
	private AGWorkflowNode m_workflowNode;
}

