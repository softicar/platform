package com.softicar.platform.core.module.event;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.event.recipient.AGSystemEventEmailRecipient;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import java.util.List;
import org.junit.Test;

public class SystemEventNotifierTest extends AbstractCoreTest {

	private final AGCoreModuleInstance moduleInstance;

	public SystemEventNotifierTest() {

		this.moduleInstance = AGCoreModuleInstance.getInstance();
	}

	@Test(expected = SofticarUserException.class)
	public void testWithoutRecipients() {

		SystemEventNotifier.notifyAboutEvents();
	}

	@Test
	public void testWithoutRecipientsAndTestSystem() {

		moduleInstance.setTestSystem(true).save();
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
	public void testWithEvents() {

		insertSystemEventEmailRecipient();
		new SystemEventBuilder(AGSystemEventSeverityEnum.ERROR, "Test").save();
		SystemEventNotifier.notifyAboutEvents();
		List<AGBufferedEmail> bufferedMails = AGBufferedEmail.TABLE.loadAll();
		assertEquals(1, bufferedMails.size());
		assertEquals(CurrentUser.get().getEmailAddress(), bufferedMails.get(0).getTo());
	}

	private void insertSystemEventEmailRecipient() {

		new AGSystemEventEmailRecipient().setRecipient(CurrentUser.get()).save();
	}
}
