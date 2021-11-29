package com.softicar.platform.workflow.module.workflow.task.delegation;

import com.softicar.platform.emf.table.listener.IEmfSaveHook;
import com.softicar.platform.workflow.module.workflow.task.WorkflowTaskNotificationSubmitter;
import java.util.Collection;

public class WorkflowTaskDelegationSaveHook implements IEmfSaveHook<AGWorkflowTaskDelegation> {

	@Override
	public void afterSave(Collection<AGWorkflowTaskDelegation> tableRows) {

		tableRows.forEach(this::notifyTargetUser);
	}

	private void notifyTargetUser(AGWorkflowTaskDelegation tableRow) {

		new WorkflowTaskNotificationSubmitter(tableRow.getWorkflowTask(), tableRow.getTargetUser()).submit();
	}
}
