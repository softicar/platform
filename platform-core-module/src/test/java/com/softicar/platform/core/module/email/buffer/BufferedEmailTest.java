package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.buffer.attachment.AGBufferedEmailAttachment;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.runtime.utils.DbAssertUtils;
import java.util.List;
import org.junit.Test;

public class BufferedEmailTest extends AbstractCoreTest {

	private final AGUser user;
	private final AGServer server;

	public BufferedEmailTest() {

		this.user = insertTestUser();
		this.server = insertDummyServer();

		AGCoreModuleInstance.getInstance().setEmailServer(server).save();
		CurrentUser.set(user);
	}

	@Test
	public void testSubmitWithDefaultValues() {

		new BufferedEmail()//
			.setFrom("from@example.com")
			.addToRecipient("to@example.com")
			.submit();

		AGBufferedEmail email = DbAssertUtils.assertOne(AGBufferedEmail.TABLE);
		assertSame(user, email.getCreatedBy());
		assertSame(server, email.getEmailServer());
		assertEquals("from@example.com", email.getFrom());
		assertEquals("", email.getReplyTo());
		assertEquals("to@example.com", email.getTo());
		assertEquals("", email.getCc());
		assertEquals("", email.getBcc());
		assertEquals("", email.getSubject());
		assertEquals("", email.getContent());
		assertEquals("text/plain;charset=utf-8", email.getContentType());
	}

	@Test(expected = RuntimeException.class)
	public void testSubmitWithoutFromAddress() {

		new BufferedEmail().addToRecipient("foo@example.com").submit();
	}

	@Test(expected = RuntimeException.class)
	public void testSubmitWithoutRecipients() {

		new BufferedEmail().setFrom("foo@example.com").submit();
	}

	@Test
	public void testSubmitWithCustomEmailServer() {

		AGServer transportServer = new AGServer()//
			.setName("Custom Transport")
			.setAddress("1.1.1.1")
			.setUsername("username")
			.setPassword("password")
			.setPort(25)
			.save();
		new BufferedEmail()//
			.setEmailServer(transportServer)
			.setFrom("sender@example.com")
			.addToRecipient("b@example.com")
			.setSubject(IDisplayString.create("This is the topic."))
			.setContent("This is some content.", EmailContentType.PLAIN)
			.submit();
		AGBufferedEmail email = DbAssertUtils.assertOne(AGBufferedEmail.TABLE);
		assertSame(transportServer, email.getEmailServer());
	}

	@Test
	public void testSubmitWithoutAttachment() {

		new BufferedEmail()//
			.setFrom("sender@example.com")
			.setReplyTo("noreply@example.com")
			.addToRecipient("a@example.com")
			.addToRecipient("b@example.com")
			.addCcRecipient("c@example.com")
			.addBccRecipient("d@example.com")
			.setSubject(IDisplayString.create("This is the topic."))
			.setContent("This is some content.", EmailContentType.PLAIN)
			.submit();

		AGBufferedEmail email = DbAssertUtils.assertOne(AGBufferedEmail.TABLE);
		assertSame(user, email.getCreatedBy());
		assertEquals("sender@example.com", email.getFrom());
		assertEquals("noreply@example.com", email.getReplyTo());
		assertEquals("a@example.com, b@example.com", email.getTo());
		assertEquals("c@example.com", email.getCc());
		assertEquals("d@example.com", email.getBcc());
		assertEquals("This is the topic.", email.getSubject());
		assertEquals("This is some content.", email.getContent());
		assertEquals("text/plain;charset=utf-8", email.getContentType());
	}

	@Test
	public void testSubmitWithAttachments() {

		byte[] data = new byte[] { 12, 33, 67, 101 };
		byte[] text = "hello".getBytes(Charsets.UTF8);
		new BufferedEmail()//
			.setFrom("from@example.com")
			.addToRecipient("to@example.com")
			.addByteArray(data, "foo", MimeType.APPLICATION_OCTET_STREAM)
			.addByteArray(text, "bar", MimeType.TEXT_PLAIN)
			.submit();

		List<AGBufferedEmailAttachment> attachments = DbAssertUtils.assertCount(2, AGBufferedEmailAttachment.TABLE);
		assertAttachment(attachments.get(0), 0, "foo", MimeType.APPLICATION_OCTET_STREAM, data);
		assertAttachment(attachments.get(1), 1, "bar", MimeType.TEXT_PLAIN, text);
	}

	@Test
	public void testSubmitWithInvalidCharactersInSubject() {

		BufferedEmail bufferedEmail = new BufferedEmail();
		bufferedEmail//
			.setFrom("from@example.com")
			.addToRecipient("to@example.com")
			.setSubject(IDisplayString.create("Some" + getNonBmpUnicodeCharacterString() + "subject"))
			.submit();

		String replacement = bufferedEmail.getInvalidCharacterReplacement();
		AGBufferedEmail email = DbAssertUtils.assertOne(AGBufferedEmail.TABLE);
		assertEquals("Some" + replacement + "subject", email.getSubject());
	}

	@Test
	public void testSubmitWithInvalidCharactersInPlainContent() {

		testSubmitWithInvalidCharactersInContent(EmailContentType.PLAIN);
	}

	@Test
	public void testSubmitWithInvalidCharactersInHtmlContent() {

		testSubmitWithInvalidCharactersInContent(EmailContentType.HTML);
	}

	private void testSubmitWithInvalidCharactersInContent(EmailContentType contentType) {

		BufferedEmail bufferedEmail = new BufferedEmail();
		bufferedEmail//
			.setFrom("from@example.com")
			.addToRecipient("to@example.com")
			.setContent("Some" + getNonBmpUnicodeCharacterString() + "content", contentType)
			.submit();

		String replacement = bufferedEmail.getInvalidCharacterReplacement();
		AGBufferedEmail email = DbAssertUtils.assertOne(AGBufferedEmail.TABLE);
		assertEquals("Some" + replacement + "content", email.getContent());
	}

	private void assertAttachment(AGBufferedEmailAttachment attachment, int index, String name, MimeType type, byte[] data) {

		assertEquals(index, attachment.getIndex());
		assertEquals(name, attachment.getName());
		assertEquals(type.getIdentifier(), attachment.getType());
		assertArrayEquals(data, attachment.getData());
	}

	private String getNonBmpUnicodeCharacterString() {

		// Unicode Character 'WINKING FACE' (U+1F609)
		return "\ud83d\ude01";
	}
}
