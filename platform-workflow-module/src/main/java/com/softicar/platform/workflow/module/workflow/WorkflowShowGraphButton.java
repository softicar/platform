package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.management.WorkflowVersionManagementPopup;

public class WorkflowShowGraphButton extends DomButton {

	private final AGWorkflowItem workflowItem;

	public WorkflowShowGraphButton(AGWorkflowItem workflowItem) {

		this.workflowItem = workflowItem;

		setIcon(WorkflowImages.WORKFLOW_GRAPH.getResource());
		setLabel(WorkflowI18n.SHOW_GRAPH);
		setTitle(WorkflowI18n.SHOW_GRAPH);
		setClickCallback(this::showGraph);
	}

	private void showGraph() {

		DomPopupManager//
			.getInstance()
			.getPopup(workflowItem, WorkflowVersionManagementPopup.class, WorkflowVersionManagementPopup::new)
			.open();
	}
}
