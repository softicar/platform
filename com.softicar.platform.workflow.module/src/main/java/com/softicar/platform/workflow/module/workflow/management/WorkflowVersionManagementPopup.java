package com.softicar.platform.workflow.module.workflow.management;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public class WorkflowVersionManagementPopup extends DomPopup implements IDomRefreshBusListener {

	private final AGWorkflowVersion workflowVersion;

	public WorkflowVersionManagementPopup(AGWorkflowVersion workflowVersion) {

		this.workflowVersion = workflowVersion;
		setCaption(WorkflowI18n.MANAGE_WORKFLOW);
		setSubCaption(workflowVersion.toDisplayWithoutId());
		appendChild(new WorkflowVersionManagementDiv(workflowVersion));
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		removeChildren();
		appendChild(new WorkflowVersionManagementDiv(workflowVersion));
	}
}
