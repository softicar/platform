package com.softicar.platform.workflow.module.workflow.node.action;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.node.action.permission.AGWorkflowNodeActionPermission;
import java.util.List;

public class AGWorkflowNodeAction extends AGWorkflowNodeActionGenerated implements IEmfObject<AGWorkflowNodeAction> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getWorkflowNode().getName() + " ;; " + getAction().toDisplay());
	}

	public List<AGWorkflowNodeActionPermission> getAllActiveWorkflowNodeActionPermissions() {

		return AGWorkflowNodeActionPermission.TABLE//
			.createSelect()
			.where(AGWorkflowNodeActionPermission.ACTIVE)
			.where(AGWorkflowNodeActionPermission.WORKFLOW_NODE_ACTION.isEqual(this))
			.list();
	}
}
