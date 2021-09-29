package com.softicar.platform.workflow.module.workflow.node.precondition;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

/**
 * This is the automatically generated class AGWorkflowNodePrecondition for
 * database table <i>Workflow.WorkflowNodePrecondition</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowNodePreconditionGenerated extends AbstractDbObject<AGWorkflowNodePrecondition> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowNodePrecondition, AGWorkflowNodePreconditionGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowNodePrecondition", AGWorkflowNodePrecondition::new, AGWorkflowNodePrecondition.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_NODE_PRECONDITION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_NODE_PRECONDITIONS);
	}

	public static final IDbIdField<AGWorkflowNodePrecondition> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowNodePrecondition, AGWorkflowNode> WORKFLOW_NODE = BUILDER.addForeignField("workflowNode", o->o.m_workflowNode, (o,v)->o.m_workflowNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.WORKFLOW_NODE);
	public static final IDbBooleanField<AGWorkflowNodePrecondition> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGWorkflowNodePrecondition, AGUuid> FUNCTION = BUILDER.addForeignField("function", o->o.m_function, (o,v)->o.m_function=v, AGUuid.ID).setTitle(WorkflowI18n.FUNCTION);
	public static final AGWorkflowNodePreconditionTable TABLE = new AGWorkflowNodePreconditionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowNodePrecondition> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowNodePrecondition get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowNodeID() {

		return getValueId(WORKFLOW_NODE);
	}

	public final AGWorkflowNode getWorkflowNode() {

		return getValue(WORKFLOW_NODE);
	}

	public final AGWorkflowNodePrecondition setWorkflowNode(AGWorkflowNode value) {

		return setValue(WORKFLOW_NODE, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowNodePrecondition setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getFunctionID() {

		return getValueId(FUNCTION);
	}

	public final AGUuid getFunction() {

		return getValue(FUNCTION);
	}

	public final AGWorkflowNodePrecondition setFunction(AGUuid value) {

		return setValue(FUNCTION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowNodePreconditionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowNode m_workflowNode;
	private Boolean m_active;
	private AGUuid m_function;
}

