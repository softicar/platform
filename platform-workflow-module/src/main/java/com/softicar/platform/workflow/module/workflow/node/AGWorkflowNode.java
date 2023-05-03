package com.softicar.platform.workflow.module.workflow.node;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import com.softicar.platform.workflow.module.workflow.node.precondition.AGWorkflowNodePrecondition;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import java.util.List;

public class AGWorkflowNode extends AGWorkflowNodeGenerated implements IEmfObject<AGWorkflowNode> {

	public static final WorkflowNodeItemCountField ITEM_COUNT_FIELD = new WorkflowNodeItemCountField();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	public boolean isEndNode() {

		return !AGWorkflowTransition.TABLE//
			.createSelect()
			.where(AGWorkflowTransition.WORKFLOW_VERSION.isEqual(getWorkflowVersion()))
			.where(AGWorkflowTransition.SOURCE_NODE.isEqual(this))
			.where(AGWorkflowTransition.ACTIVE)
			.exists();
	}

	public List<AGWorkflowNodeAction> getAllActiveWorkflowNodeActions() {

		return AGWorkflowNodeAction.TABLE//
			.createSelect()
			.where(AGWorkflowNodeAction.ACTIVE)
			.where(AGWorkflowNodeAction.WORKFLOW_NODE.isEqual(this))
			.list();
	}

	public List<AGWorkflowNodePrecondition> getAllActiveWorkflowNodePreconditions() {

		return AGWorkflowNodePrecondition.TABLE//
			.createSelect()
			.where(AGWorkflowNodePrecondition.ACTIVE)
			.where(AGWorkflowNodePrecondition.WORKFLOW_NODE.isEqual(this))
			.list();
	}

	public List<AGWorkflowItem> getAllWorkflowItems() {

		return AGWorkflowItem//
			.createSelect()
			.join(AGWorkflowItem.WORKFLOW_NODE)
			.where(AGWorkflowNode.ID.isEqual(getThis().getId()))
			.list();
	}

	public AGWorkflow getWorkflow() {

		return getWorkflowVersion().getWorkflow();
	}
}
