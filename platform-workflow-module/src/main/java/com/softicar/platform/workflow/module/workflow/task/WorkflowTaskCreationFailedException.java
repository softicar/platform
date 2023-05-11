package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

public class WorkflowTaskCreationFailedException extends SofticarException {

	public WorkflowTaskCreationFailedException(Throwable cause, AGWorkflowItem item) {

		super(cause, "Task creation failed for item #%s.", item.getId());
	}
}
