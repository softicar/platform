package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import org.junit.Test;

public class WorkflowTaskNotificationSubmitterTest extends AbstractTestObjectWorkflowTest {

	private final AGWorkflowTask task;

	public WorkflowTaskNotificationSubmitterTest() {

		AGWorkflowItem item = insertWorkflowItem(rootNode);
		this.task = insertWorkflowTaskOpen(user, item);
	}

	@Test
	public void testWithEnglishLanguage() {

		user//
			.setPreferredLanguage(LanguageEnum.ENGLISH)
			.save();

		new WorkflowTaskNotificationSubmitter(task).submit();

		AGBufferedEmail email = assertOne(AGBufferedEmail.TABLE.loadAll());
		assertEquals("New Workflow Task", email.getSubject());
		assertEquals(
			"A new workflow task requires your attention."//
					+ "<br/><br/>"//
					+ "<a href=\"" + getMyTasksPageUrl() + "\" target=\"_blank\">My Tasks</a>",
			email.getContent());
	}

	@Test
	public void testWithGermanLanguage() {

		user//
			.setPreferredLanguage(LanguageEnum.GERMAN)
			.save();

		new WorkflowTaskNotificationSubmitter(task).submit();

		AGBufferedEmail email = assertOne(AGBufferedEmail.TABLE.loadAll());
		assertEquals("Neue Arbeitsablauf-Aufgabe", email.getSubject());
		assertEquals(
			"Eine neue Arbeitsablauf-Aufgabe erfordert Ihre Aufmerksamkeit."//
					+ "<br/><br/>"//
					+ "<a href=\"" + getMyTasksPageUrl() + "\" target=\"_blank\">Meine Aufgaben</a>",
			email.getContent());
	}

	@Test
	public void testWithDifferentRecipient() {

		AGUser differentUser = insertUser("Different User")//
			.setEmailAddress("differentUser@example.com")
			.setPreferredLanguage(LanguageEnum.GERMAN)
			.save();

		new WorkflowTaskNotificationSubmitter(task).setNotificationRecipient(differentUser).submit();

		AGBufferedEmail email = assertOne(AGBufferedEmail.TABLE.loadAll());
		assertEquals("differentUser@example.com", email.getTo());
		assertEquals("Neue Arbeitsablauf-Aufgabe", email.getSubject());
		assertEquals(
			"Eine neue Arbeitsablauf-Aufgabe erfordert Ihre Aufmerksamkeit."//
					+ "<br/><br/>"//
					+ "<a href=\"" + getMyTasksPageUrl() + "\" target=\"_blank\">Meine Aufgaben</a>",
			email.getContent());

	}

	private String getMyTasksPageUrl() {

		return new PageUrlBuilder<>(WorkflowTaskPage.class, moduleInstance).build().toString();
	}
}
