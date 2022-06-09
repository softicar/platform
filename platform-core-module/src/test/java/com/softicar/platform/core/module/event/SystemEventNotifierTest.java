package com.softicar.platform.core.module.event;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.event.recipient.AGSystemEventEmailRecipient;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import org.junit.Test;

public class SystemEventNotifierTest extends AbstractCoreTest {

	private final AGCoreModuleInstance moduleInstance;

	public SystemEventNotifierTest() {

		this.moduleInstance = AGCoreModuleInstance.getInstance();
	}

	@Test
	public void testWithoutRecipients() {

		assertException(//
			SofticarUserException.class,
			SystemEventNotifier::notifyAboutEvents,
			CoreI18n.NO_EMAIL_RECIPIENTS_DEFINED.toString());
	}

	@Test
	public void testWithoutRecipientsAndTestSystem() {

		moduleInstance.setTestSystem(true).save();
		SystemEventNotifier.notifyAboutEvents();
		assertEquals(0, AGBufferedEmail.TABLE.countAll());
	}

	@Test
	public void testWithoutRecipientsAndEventAndTestSystem() {

		moduleInstance.setTestSystem(true).save();
		insetSystemEvent(AGSystemEventSeverityEnum.ERROR);
		SystemEventNotifier.notifyAboutEvents();
		assertEquals(0, AGBufferedEmail.TABLE.countAll());
	}

	@Test
	public void testWithoutEvents() {

		insertSystemEventEmailRecipient();
		SystemEventNotifier.notifyAboutEvents();
		assertEquals(0, AGBufferedEmail.TABLE.countAll());
	}

	@Test
	public void testWithError() {

		insertSystemEventEmailRecipient();
		insetSystemEvent(AGSystemEventSeverityEnum.ERROR);

		SystemEventNotifier.notifyAboutEvents();

		assertEmailSent();
	}

	@Test
	public void testWithWarning() {

		insertSystemEventEmailRecipient();
		insetSystemEvent(AGSystemEventSeverityEnum.WARNING);

		SystemEventNotifier.notifyAboutEvents();

		assertEmailSent();
	}

	@Test
	public void testWithInfo() {

		insertSystemEventEmailRecipient();
		insetSystemEvent(AGSystemEventSeverityEnum.INFORMATION);

		SystemEventNotifier.notifyAboutEvents();

		assertEquals(0, AGBufferedEmail.TABLE.countAll());
	}

	private void insertSystemEventEmailRecipient() {

		new AGSystemEventEmailRecipient().setRecipient(CurrentUser.get()).save();
	}

	private void insetSystemEvent(AGSystemEventSeverityEnum severity) {

		new SystemEventBuilder(severity, "Test").save();
	}

	private void assertEmailSent() {

		var bufferedMails = AGBufferedEmail.TABLE.loadAll();
		assertEquals(1, bufferedMails.size());
		assertEquals(CurrentUser.get().getEmailAddress(), bufferedMails.get(0).getTo());
	}
}
