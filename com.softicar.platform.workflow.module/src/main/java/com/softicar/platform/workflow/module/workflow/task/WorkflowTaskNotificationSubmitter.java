package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.i18n.LanguageScope;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.buffer.BufferedEmailFactory;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

public class WorkflowTaskNotificationSubmitter {

	private final AGWorkflowTask task;

	public WorkflowTaskNotificationSubmitter(AGWorkflowTask task) {

		this.task = task;
	}

	public void submit() {

		try (LanguageScope scope = new LanguageScope(task.getUser().getLanguageEnum())) {
			BufferedEmailFactory//
				.createNoReplyEmail()
				.addToRecipient(task.getUser())
				.setSubject(WorkflowI18n.NEW_WORKFLOW_TASK)
				.setContent(
					String
						.format(//
							"%s<br/><br/><a href=\"%s\" target=\"_blank\">%s</a>",
							WorkflowI18n.A_NEW_WORKFLOW_TASK_REQUIRES_YOUR_ATTENTION,
							new PageUrlBuilder<>(WorkflowTaskPage.class, task.getWorkflowItem().getWorkflow().getModuleInstance()).build(),
							WorkflowI18n.MY_TASKS),
					EmailContentType.HTML)
				.submit();
		}
	}
}
