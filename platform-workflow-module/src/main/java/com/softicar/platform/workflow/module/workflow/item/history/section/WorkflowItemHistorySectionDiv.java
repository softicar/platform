package com.softicar.platform.workflow.module.workflow.item.history.section;

import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.item.history.WorkflowItemHistoryDiv;

public class WorkflowItemHistorySectionDiv<R extends IWorkflowableObject<R>> extends EmfFormSectionDiv<R> {

	public WorkflowItemHistorySectionDiv(IEmfFormBody<R> formBody) {

		super(formBody, new WorkflowItemHistorySectionHeader());

		var workflowItem = formBody.getTableRow().getWorkflowItem();
		if (workflowItem != null) {
			addElement(() -> new WorkflowItemHistoryDiv(workflowItem));
		}
	}
}
