package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.WorkflowI18n;

public class AGWorkflowItemMessage extends AGWorkflowItemMessageGenerated implements IEmfObject<AGWorkflowItemMessage> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return WorkflowI18n.MESSAGE_FOR_ITEM_ARG1_AT_ARG2.toDisplay(getWorkflowItem().toDisplayWithoutId(), getTransaction().getAt());
	}
}
