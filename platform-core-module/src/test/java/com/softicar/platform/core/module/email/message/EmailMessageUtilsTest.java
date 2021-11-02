package com.softicar.platform.core.module.email.message;

import com.softicar.platform.common.testing.Asserts;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.junit.Assert;
import org.junit.Test;

public class EmailMessageUtilsTest extends Assert {

	@Test
	public void testGetReferencedMessageIds() throws MessagingException {

		MimeMessage message = new MimeMessage(Session.getInstance(new Properties()));
		EmailMessageId reference1 = new EmailMessageId("<foo@example.com>");
		EmailMessageId reference2 = new EmailMessageId("<bar@example.com>");
		EmailMessageId reference3 = new EmailMessageId("<baz@example.com>");
		message.addHeader("References", reference1.getWithAngleBrackets() + " " + reference2.getWithAngleBrackets());
		message.addHeader("References", reference3.getWithAngleBrackets());

		List<EmailMessageId> messageIds = EmailMessageUtils.getReferencedMessageIds(message);
		Asserts.assertCount(3, messageIds);
		assertEquals(reference1, messageIds.get(0));
		assertEquals(reference2, messageIds.get(1));
		assertEquals(reference3, messageIds.get(2));
	}
}
