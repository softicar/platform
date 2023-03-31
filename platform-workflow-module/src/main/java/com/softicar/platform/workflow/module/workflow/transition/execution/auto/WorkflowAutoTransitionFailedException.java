package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

public class WorkflowAutoTransitionFailedException extends SofticarException {

	public WorkflowAutoTransitionFailedException(Throwable cause, AGWorkflowItem item) {

		super(cause, "Auto transition failed for item: %s", item);
	}
}
