package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import java.util.List;

public class AGWorkflowVersion extends AGWorkflowVersionGenerated implements IEmfObject<AGWorkflowVersion> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getWorkflow().getName());
	}

	public boolean isConsistent() {

		//TODO add more consistency checks
		return getRootNode() != null;
	}

	public List<AGWorkflowNode> getAllActiveWorkflowNodes() {

		return AGWorkflowNode.TABLE//
			.createSelect()
			.where(AGWorkflowNode.WORKFLOW_VERSION.isEqual(this))
			.where(AGWorkflowNode.ACTIVE)
			.list();
	}

	public List<AGWorkflowTransition> getAllActiveTransitions() {

		return AGWorkflowTransition.TABLE//
			.createSelect()
			.where(AGWorkflowTransition.WORKFLOW_VERSION.isEqual(this))
			.where(AGWorkflowTransition.ACTIVE)
			.list();
	}

	public boolean isCurrentVersion() {

		return getWorkflow().getCurrentVersion().is(getThis());
	}
}
