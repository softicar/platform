package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.core.module.email.EmailSystemProperties;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.db.runtime.utils.DbAssertUtils;
import org.junit.Test;

public class BufferedEmailSenderTest extends AbstractCoreTest {

	private final AGServer testServer;

	public BufferedEmailSenderTest() {

		this.testServer = insertServer("Server", "1.1.1.1", 25, "domain", "username", "password");
		insertServer("Server", "1.1.1.1", 25, "domain", "username", "password");
		System.setProperty(EmailSystemProperties.SENDING_ENABLED.getPropertyName().toString(), "false");
		System.setProperty(EmailSystemProperties.DUMPING_ENABLED.getPropertyName().toString(), "false");
	}

	@Test
	public void testSendActiveMail() {

		new BufferedEmail()//
			.setEmailServer(testServer)
			.setFrom("from@example.com")
			.addToRecipient("to@example.com")
			.submit();

		AGBufferedEmail email = DbAssertUtils.assertOne(AGBufferedEmail.TABLE);
		assertNull(email.getSentAt());

		new BufferedEmailSender(testServer).sendAll();

		assertNotNull(email.getSentAt());
	}

	@Test
	public void testSendInactiveMail() {

		new BufferedEmail()//
			.setEmailServer(testServer)
			.setFrom("from@example.com")
			.addToRecipient("to@example.com")
			.submit();
		AGBufferedEmail.TABLE.loadAll().get(0).setActive(false).save();

		AGBufferedEmail email = DbAssertUtils.assertOne(AGBufferedEmail.TABLE);
		assertNull(email.getSentAt());

		new BufferedEmailSender(testServer).sendAll();

		assertNull(email.getSentAt());
	}
}
