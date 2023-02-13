package com.softicar.platform.workflow.module.workflow.transients.field.display;

import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.management.WorkflowVersionManagementPopup;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

public class WorkflowNodeFieldDisplay extends DomBar {

	public WorkflowNodeFieldDisplay(AGWorkflowNode workflowNode) {

		if (workflowNode != null) {
			appendText(workflowNode.getName());

			new DomPopupButton()//
				.setPopupFactory(() -> new WorkflowVersionManagementPopup(workflowNode))
				.setIcon(EmfImages.ENTITY_VIEW.getResource())
				.setTitle(WorkflowI18n.SHOW_GRAPH)
				.appendTo(this);
		}
	}
}
