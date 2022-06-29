package com.softicar.platform.core.module.event;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import org.junit.Test;

public class SystemEventNotifierTest extends AbstractCoreTest {

	@Test
	public void testWithoutEvents() {

		SystemEventNotifier.notifyAboutEvents();
		assertEquals(0, AGBufferedEmail.TABLE.countAll());
	}

	@Test
	public void testWithError() {

		insetSystemEvent(AGSystemEventSeverityEnum.ERROR);

		SystemEventNotifier.notifyAboutEvents();

		assertEmailSent();
	}

	@Test
	public void testWithWarning() {

		insetSystemEvent(AGSystemEventSeverityEnum.WARNING);

		SystemEventNotifier.notifyAboutEvents();

		assertEmailSent();
	}

	@Test
	public void testWithInfo() {

		insetSystemEvent(AGSystemEventSeverityEnum.INFORMATION);

		SystemEventNotifier.notifyAboutEvents();

		assertEquals(0, AGBufferedEmail.TABLE.countAll());
	}

	private void insetSystemEvent(AGSystemEventSeverityEnum severity) {

		new SystemEventBuilder(severity, "Test").save();
	}

	private void assertEmailSent() {

		var bufferedMails = AGBufferedEmail.TABLE.loadAll();
		assertEquals(1, bufferedMails.size());
		String emailAddress = AGCoreModuleInstance.getInstance().getSupportEmailAddress();
		assertNotNull(emailAddress);
		assertEquals(emailAddress, bufferedMails.get(0).getTo());
	}
}
