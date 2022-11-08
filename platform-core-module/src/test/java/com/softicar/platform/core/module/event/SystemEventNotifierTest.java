package com.softicar.platform.core.module.event;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import org.junit.Test;

public class SystemEventNotifierTest extends AbstractCoreTest {

	private static final String MESSAGE = "Test";
	private final AGServer server;

	public SystemEventNotifierTest() {

		this.server = insertDummyServer();

		AGCoreModuleInstance.getInstance().setEmailServer(server).save();
	}

	@Test
	public void testWithoutEvents() {

		SystemEventNotifier.notifyAboutEvents();
		assertEquals(0, AGBufferedEmail.TABLE.countAll());
	}

	@Test
	public void testWithError() {

		insertSystemErrorEvent(MESSAGE);

		SystemEventNotifier.notifyAboutEvents();

		assertEmailSent();
	}

	@Test
	public void testWithWarning() {

		insertSystemWarningEvent(MESSAGE);

		SystemEventNotifier.notifyAboutEvents();

		assertEmailSent();
	}

	@Test
	public void testWithInfo() {

		insertSystemInformationEvent(MESSAGE);

		SystemEventNotifier.notifyAboutEvents();

		assertEquals(0, AGBufferedEmail.TABLE.countAll());
	}

	private void assertEmailSent() {

		var bufferedMails = AGBufferedEmail.TABLE.loadAll();
		assertEquals(1, bufferedMails.size());
		String emailAddress = AGCoreModuleInstance.getInstance().getSupportEmailAddress();
		assertNotNull(emailAddress);
		assertEquals(emailAddress, bufferedMails.get(0).getTo());
	}
}
