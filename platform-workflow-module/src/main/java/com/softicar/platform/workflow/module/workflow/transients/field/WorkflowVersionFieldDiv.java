package com.softicar.platform.workflow.module.workflow.transients.field;

import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.management.WorkflowVersionManagementPopup;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.transients.field.display.WorkflowNodeFieldDisplay;
import java.util.Optional;

public class WorkflowVersionFieldDiv extends WorkflowNodeFieldDisplay {

	public WorkflowVersionFieldDiv(Optional<AGWorkflowNode> workflowNode) {

		super(workflowNode);
		workflowNode.ifPresent(this::buildVersionManagementButton);
	}

	private void buildVersionManagementButton(AGWorkflowNode workflowNode) {

		appendChild(
			new DomPopupButton()//
				.setPopupFactory(() -> new WorkflowVersionManagementPopup(workflowNode))
				.setIcon(EmfImages.ENTITY_VIEW.getResource())
				.setTitle(WorkflowI18n.WORKFLOW)
				.setStyle(CssStyle.VERTICAL_ALIGN, "middle"));
	}
}
