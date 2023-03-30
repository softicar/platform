package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import com.softicar.platform.workflow.module.workflow.node.action.permission.AGWorkflowNodeActionPermission;
import com.softicar.platform.workflow.module.workflow.node.precondition.AGWorkflowNodePrecondition;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.permission.AGWorkflowTransitionPermission;
import java.util.Collection;
import java.util.List;

public class AGWorkflowVersion extends AGWorkflowVersionGenerated implements IEmfObject<AGWorkflowVersion> {

	public static final WorkflowVersionHashField HASH_FIELD = new WorkflowVersionHashField();
	public static final WorkflowVersionItemCountField ITEM_COUNT_FIELD = new WorkflowVersionItemCountField();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getWorkflow().getName());
	}

	public List<AGWorkflowNode> getAllActiveWorkflowNodes() {

		return AGWorkflowNode.TABLE//
			.createSelect()
			.where(AGWorkflowNode.ACTIVE)
			.where(AGWorkflowNode.WORKFLOW_VERSION.isEqual(this))
			.orderBy(AGWorkflowNode.ID)
			.list();
	}

	public List<AGWorkflowNodeAction> getAllActiveWorkflowNodeActions() {

		return AGWorkflowNodeAction.TABLE//
			.createSelect()
			.where(AGWorkflowNodeAction.ACTIVE)
			.orderBy(AGWorkflowNodeAction.ID)
			.join(AGWorkflowNodeAction.WORKFLOW_NODE)
			.where(AGWorkflowNode.ACTIVE)
			.where(AGWorkflowNode.WORKFLOW_VERSION.isEqual(this))
			.list();
	}

	public List<AGWorkflowNodeActionPermission> getAllActiveWorkflowNodeActionPermissions() {

		return AGWorkflowNodeActionPermission.TABLE//
			.createSelect()
			.where(AGWorkflowNodeActionPermission.ACTIVE)
			.orderBy(AGWorkflowNodeActionPermission.ID)
			.join(AGWorkflowNodeActionPermission.WORKFLOW_NODE_ACTION)
			.where(AGWorkflowNodeAction.ACTIVE)
			.join(AGWorkflowNodeAction.WORKFLOW_NODE)
			.where(AGWorkflowNode.ACTIVE)
			.where(AGWorkflowNode.WORKFLOW_VERSION.isEqual(this))
			.list();
	}

	public List<AGWorkflowNodePrecondition> getAllActiveWorkflowNodePreconditions() {

		return AGWorkflowNodePrecondition.TABLE//
			.createSelect()
			.where(AGWorkflowNodePrecondition.ACTIVE)
			.orderBy(AGWorkflowNodePrecondition.ID)
			.join(AGWorkflowNodePrecondition.WORKFLOW_NODE)
			.where(AGWorkflowNode.ACTIVE)
			.where(AGWorkflowNode.WORKFLOW_VERSION.isEqual(this))
			.list();
	}

	public List<AGWorkflowTransition> getAllActiveTransitions() {

		return AGWorkflowTransition.TABLE//
			.createSelect()
			.where(AGWorkflowTransition.ACTIVE)
			.where(AGWorkflowTransition.WORKFLOW_VERSION.isEqual(this))
			.orderBy(AGWorkflowTransition.ID)
			.list();
	}

	public List<AGWorkflowTransitionPermission> getAllActiveTransitionPermissions() {

		return AGWorkflowTransitionPermission.TABLE//
			.createSelect()
			.where(AGWorkflowTransitionPermission.ACTIVE)
			.orderBy(AGWorkflowTransitionPermission.ID)
			.join(AGWorkflowTransitionPermission.TRANSITION)
			.where(AGWorkflowTransition.ACTIVE)
			.where(AGWorkflowTransition.WORKFLOW_VERSION.isEqual(this))
			.list();
	}

	public boolean isCurrentVersion() {

		return is(getWorkflow().getCurrentVersion());
	}

	public static Collection<AGWorkflowVersion> getCurrentVersions() {

		return AGWorkflowVersion.TABLE//
			.createSelect()
			.joinReverse(AGWorkflow.CURRENT_VERSION)
			.list();
	}
}
