package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.locale.LocaleScope;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.buffer.BufferedEmailFactory;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.Objects;

public class WorkflowTaskNotificationSubmitter {

	private final AGWorkflowTask task;
	private AGUser notificationRecipient;

	public WorkflowTaskNotificationSubmitter(AGWorkflowTask task) {

		this.task = task;
		this.notificationRecipient = task.getUser();
	}

	public WorkflowTaskNotificationSubmitter setNotificationRecipient(AGUser notificationRecipient) {

		this.notificationRecipient = Objects.requireNonNull(notificationRecipient);
		return this;
	}

	public void submit() {

		try (var scope = new LocaleScope(notificationRecipient.getLocale())) {
			BufferedEmailFactory//
				.createNoReplyEmail()
				.addToRecipient(notificationRecipient)
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
