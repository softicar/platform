package com.softicar.platform.workflow.module.workflow.node;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import com.softicar.platform.workflow.module.workflow.node.precondition.AGWorkflowNodePrecondition;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import java.util.List;

public class AGWorkflowNode extends AGWorkflowNodeGenerated implements IEmfObject<AGWorkflowNode> {

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
}
