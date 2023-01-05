package com.softicar.platform.core.module.email.part.chooser;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.email.part.AbstractEmailPartsTest;
import com.softicar.platform.core.module.email.part.sequencer.EmailPartsSequencer;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.junit.Test;

public class EmailAlternativePartsByTypeChooserTest extends AbstractEmailPartsTest {

	private final EmailAlternativePartsByTypeChooser chooser;

	public EmailAlternativePartsByTypeChooserTest() {

		this.chooser = new EmailAlternativePartsByTypeChooser();
	}

	@Test
	public void testWithoutPreferenceList() throws MessagingException {

		var textPart = createMimePart("text/plain", "One");
		var htmlPart = createMimePart("text/html", "One");

		assertPart(textPart, createMimeMessageWithAlternativeParts(textPart, htmlPart));
		assertPart(htmlPart, createMimeMessageWithAlternativeParts(htmlPart, textPart));
	}

	@Test
	public void testWithPreferenceList() throws MessagingException {

		chooser//
			.addType(MimeType.TEXT_HTML)
			.addType(MimeType.TEXT_PLAIN);

		var textPart = createMimePart("text/plain", "One");
		var htmlPart = createMimePart("text/html", "One");
		var otherPart = createMimePart("text/other", "One");

		// with single part
		assertPart(otherPart, createMimeMessageWithAlternativeParts(otherPart));
		assertPart(textPart, createMimeMessageWithAlternativeParts(textPart));
		assertPart(htmlPart, createMimeMessageWithAlternativeParts(htmlPart));

		// with multiple alternative parts
		assertPart(otherPart, createMimeMessageWithAlternativeParts(otherPart, createMimePart("text/other", "More")));
		assertPart(textPart, createMimeMessageWithAlternativeParts(otherPart, textPart));
		assertPart(htmlPart, createMimeMessageWithAlternativeParts(otherPart, textPart, htmlPart));
		assertPart(htmlPart, createMimeMessageWithAlternativeParts(otherPart, textPart, htmlPart, createMimePart("text/html", "More")));
	}

	private void assertPart(Part expectedPart, MimeMessage message) {

		var parts = new EmailPartsSequencer(message).setAlternativeChooser(chooser).getParts();
		assertSame(expectedPart, assertOne(parts));
	}

	private MimeMessage createMimeMessageWithAlternativeParts(BodyPart...alternativeParts) throws MessagingException {

		return createMimeMessage(new MimeMultipart("alternative", alternativeParts));
	}
}
