package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

public class WorkflowItemMessagePopup extends DomPopup {

	public WorkflowItemMessagePopup(AGWorkflowItem item) {

		setCaption(WorkflowI18n.MESSAGES);
		setSubCaption(item.toDisplayWithoutId());
		appendChild(new WorkflowItemMessageDiv(item));
	}
}
