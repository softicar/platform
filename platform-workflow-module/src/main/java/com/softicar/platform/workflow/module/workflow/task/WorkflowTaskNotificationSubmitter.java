package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.i18n.LanguageScope;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.buffer.BufferedEmailFactory;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.WorkflowI18n;

public class WorkflowTaskNotificationSubmitter {

	private final AGWorkflowTask task;
	private final AGUser user;

	public WorkflowTaskNotificationSubmitter(AGWorkflowTask task) {

		this.task = task;
		this.user = task.getUser();
	}

	public WorkflowTaskNotificationSubmitter(AGWorkflowTask task, AGUser user) {

		this.task = task;
		this.user = user;
	}

	public void submit() {

		try (LanguageScope scope = new LanguageScope(user.getLanguageEnum())) {
			BufferedEmailFactory//
				.createNoReplyEmail()
				.addToRecipient(user)
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
