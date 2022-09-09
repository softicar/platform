package com.softicar.platform.workflow.module.workflow.node.action;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

/**
 * This is the automatically generated class AGWorkflowNodeAction for
 * database table <i>Workflow.WorkflowNodeAction</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowNodeActionGenerated extends AbstractDbObject<AGWorkflowNodeAction> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowNodeAction, AGWorkflowNodeActionGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowNodeAction", AGWorkflowNodeAction::new, AGWorkflowNodeAction.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_NODE_ACTIONS);
	}

	public static final IDbIdField<AGWorkflowNodeAction> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowNodeAction, AGWorkflowNode> WORKFLOW_NODE = BUILDER.addForeignField("workflowNode", o->o.m_workflowNode, (o,v)->o.m_workflowNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.WORKFLOW_NODE).setForeignKeyName("WorkflowNodeAction_ibfk_1");
	public static final IDbBooleanField<AGWorkflowNodeAction> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGWorkflowNodeAction, AGUuid> ACTION = BUILDER.addForeignField("action", o->o.m_action, (o,v)->o.m_action=v, AGUuid.ID).setTitle(WorkflowI18n.ACTION).setForeignKeyName("WorkflowNodeAction_ibfk_2");
	public static final IDbKey<AGWorkflowNodeAction> UK_WORKFLOW_NODE_ACTION = BUILDER.addUniqueKey("workflowNodeAction", WORKFLOW_NODE, ACTION);
	public static final IDbKey<AGWorkflowNodeAction> IK_ACTION = BUILDER.addIndexKey("action", ACTION);
	public static final AGWorkflowNodeActionTable TABLE = new AGWorkflowNodeActionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowNodeAction> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowNodeAction get(Integer id) {

		return TABLE.get(id);
	}

	public static AGWorkflowNodeAction loadByWorkflowNodeAndAction(AGWorkflowNode workflowNode, AGUuid action) {

		return TABLE//
				.createSelect()
				.where(WORKFLOW_NODE.equal(workflowNode))
				.where(ACTION.equal(action))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowNodeID() {

		return getValueId(WORKFLOW_NODE);
	}

	public final AGWorkflowNode getWorkflowNode() {

		return getValue(WORKFLOW_NODE);
	}

	public final AGWorkflowNodeAction setWorkflowNode(AGWorkflowNode value) {

		return setValue(WORKFLOW_NODE, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowNodeAction setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getActionID() {

		return getValueId(ACTION);
	}

	public final AGUuid getAction() {

		return getValue(ACTION);
	}

	public final AGWorkflowNodeAction setAction(AGUuid value) {

		return setValue(ACTION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowNodeActionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowNode m_workflowNode;
	private Boolean m_active;
	private AGUuid m_action;
}

