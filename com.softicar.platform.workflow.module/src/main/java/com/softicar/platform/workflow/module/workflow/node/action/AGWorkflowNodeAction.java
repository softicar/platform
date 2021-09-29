package com.softicar.platform.workflow.module.workflow.node.action;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.node.action.role.AGWorkflowNodeActionRole;
import java.util.List;

public class AGWorkflowNodeAction extends AGWorkflowNodeActionGenerated implements IEmfObject<AGWorkflowNodeAction> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getWorkflowNode().getName() + " ;; " + getAction().toDisplay());
	}

	public List<AGWorkflowNodeActionRole> getAllActiveWorkflowNodeActionRoles() {

		return AGWorkflowNodeActionRole.TABLE//
			.createSelect()
			.where(AGWorkflowNodeActionRole.ACTIVE)
			.where(AGWorkflowNodeActionRole.WORKFLOW_NODE_ACTION.equal(this))
			.list();
	}
}
