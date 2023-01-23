package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

public class WorkflowItemMessageCreationButton extends DomPopupButton {

	public WorkflowItemMessageCreationButton(AGWorkflowItem item) {

		setPopupFactory(() -> new EmfFormPopup<>(new AGWorkflowItemMessage().setWorkflowItem(item)));
		setIcon(EmfImages.ENTITY_CREATE.getResource());
		setLabel(WorkflowI18n.ADD_NEW_MESSAGE);
		setTitle(WorkflowI18n.ADD_NEW_MESSAGE);
		setEnabled(WorkflowPermissions.OPERATION.test(item.getWorkflow().getModuleInstance(), CurrentBasicUser.get()));
	}
}
