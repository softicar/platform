package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.message.EmailMessageId;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

public class BufferedEmailToMimeMessageConverterTest extends Asserts {

	private static final EmailMessageId ORIGINAL_MESSAGE_ID = new EmailMessageId("<original-message@example.org>");
	private static final EmailMessageId OTHER_MESSAGE_ID_A = new EmailMessageId("<other-message-a@example.org>");
	private static final EmailMessageId OTHER_MESSAGE_ID_B = new EmailMessageId("<other-message-b@example.org>");
	private static final EmailMessageId THIS_MESSAGE_ID = new EmailMessageId("<this-message@example.org>");
	private static final byte[] ATTACHMENT_A = new byte[] { 1, 2, 3 };
	private static final byte[] ATTACHMENT_B = new byte[] { 3, 2, 1 };

	@Test
	@SuppressWarnings("resource")
	public void test() throws MessagingException, IOException {

		var email = new BufferedEmail();

		// setup addresses
		email.setFrom("from@example.org");
		email.setSender("sender@example.org");
		email.addToRecipient("to1@example.org");
		email.addToRecipient("to2@example.org");
		email.addCcRecipient("cc1@example.org");
		email.addCcRecipient("cc2@example.org");
		email.addBccRecipient("bcc1@example.org");
		email.addBccRecipient("bcc2@example.org");

		// setup message IDs
		email.setInReplyTo(ORIGINAL_MESSAGE_ID);
		email.setMessageId(THIS_MESSAGE_ID);
		email.addReferences(List.of(OTHER_MESSAGE_ID_A, OTHER_MESSAGE_ID_B));

		// setup other headers
		email.setAutoSubmitted("auto-generated");

		// setup content
		email.addByteArray(ATTACHMENT_A, "attachmentA.txt", MimeType.APPLICATION_OCTET_STREAM);
		email.addByteArray(ATTACHMENT_B, "attachmentB.txt", MimeType.APPLICATION_PDF);
		email.setContent("<b>body</b>", EmailContentType.HTML);

		// execute the actual conversion
		var mimeMessage = new BufferedEmailToMimeMessageConverter(email).convert();

		// verify addresses
		assertEquals("from@example.org", mimeMessage.getHeader("from", " "));
		assertEquals("sender@example.org", mimeMessage.getHeader("sender", " "));
		assertEquals("to1@example.org, to2@example.org", mimeMessage.getHeader("to", " "));
		assertEquals("cc1@example.org, cc2@example.org", mimeMessage.getHeader("cc", " "));
		assertEquals("bcc1@example.org, bcc2@example.org", mimeMessage.getHeader("bcc", " "));

		// verify message IDs
		assertEquals(THIS_MESSAGE_ID.toString(), mimeMessage.getHeader("message-id", " "));
		assertEquals(ORIGINAL_MESSAGE_ID.toString(), mimeMessage.getHeader("in-reply-to", " "));
		assertEquals(OTHER_MESSAGE_ID_A + " " + OTHER_MESSAGE_ID_B, mimeMessage.getHeader("references", " "));

		// verify other headers
		assertEquals("auto-generated", mimeMessage.getHeader("auto-submitted", " "));

		// verify content
		var content = (Multipart) mimeMessage.getContent();
		assertEquals(3, content.getCount());
		assertEquals("<b>body</b>", content.getBodyPart(0).getContent());
		assertEquals("text/html; charset=utf-8", content.getBodyPart(0).getContentType());
		assertArrayEquals(ATTACHMENT_A, StreamUtils.toByteArray(content.getBodyPart(1).getInputStream()));
		assertEquals(MimeType.APPLICATION_OCTET_STREAM.getIdentifier() + "; name=attachmentA.txt", content.getBodyPart(1).getContentType());
		assertArrayEquals(ATTACHMENT_B, StreamUtils.toByteArray(content.getBodyPart(2).getInputStream()));
		assertEquals(MimeType.APPLICATION_PDF.getIdentifier() + "; name=attachmentB.txt", content.getBodyPart(2).getContentType());
	}
}
