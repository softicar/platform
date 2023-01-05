package com.softicar.platform.core.module.email.part.sequencer;

import com.softicar.platform.core.module.email.part.AbstractEmailPartsTest;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import org.junit.Test;

public class EmailPartsSequencerTest extends AbstractEmailPartsTest {

	@Test
	public void testWithEmptyMultipart() throws MessagingException {

		var message = createMimeMessage(new MimeMultipart());

		var parts = new EmailPartsSequencer(message).getParts();

		assertEmpty(parts);
	}

	@Test
	public void testWithAlternativeMultipart() throws MessagingException {

		var part1 = createMimePart("text/plain", "Hello");
		var part2 = createMimePart("text/plain", "Hello");
		var message = createMimeMessage(new MimeMultipart("alternative", part1, part2));

		var parts = new EmailPartsSequencer(message).getParts();

		assertSame(part1, assertOne(parts));
	}

	@Test
	public void testWithMixedMultipart() throws MessagingException {

		var part1 = createMimePart("text/plain", "Hello");
		var part2 = createMimePart("text/plain", "Hello");
		var message = createMimeMessage(new MimeMultipart(part1, part2));

		var parts = new EmailPartsSequencer(message).getParts();

		var partsList = assertCount(2, parts);
		assertSame(part1, partsList.get(0));
		assertSame(part2, partsList.get(1));
	}
}
