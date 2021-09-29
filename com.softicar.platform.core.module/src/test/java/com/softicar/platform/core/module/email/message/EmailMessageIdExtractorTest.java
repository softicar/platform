package com.softicar.platform.core.module.email.message;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

public class EmailMessageIdExtractorTest extends Assert {

	private static final String FIRST_ID = "<aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa.spm@example.com>";
	private static final String SECOND_ID = "<bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb@example.com>";
	private static final String ID_WITHOUT_CLOSING_BRACKET = "<cccccccc-cccc-cccc-cccc-cccccccccccc@example.com";

	@Test
	public void testExctractAllWithEmptyInput() {

		EmailMessageIdExtractor extractor = createExtractor("");

		Collection<EmailMessageId> messageIds = extractor.extractAll();

		assertTrue(messageIds.isEmpty());
	}

	@Test
	public void testExctractAllWithSingleId() {

		EmailMessageIdExtractor extractor = createExtractor(FIRST_ID);

		Collection<EmailMessageId> messageIds = extractor.extractAll();

		assertEquals(1, messageIds.size());
		assertEquals(FIRST_ID, messageIds.iterator().next().getWithAngleBrackets());
	}

	@Test
	public void testExctractAllWithSpaceSeparation() {

		EmailMessageIdExtractor extractor = createExtractor(FIRST_ID + " " + SECOND_ID);

		Collection<EmailMessageId> messageIds = extractor.extractAll();

		ArrayList<EmailMessageId> messageIdList = new ArrayList<>(messageIds);
		assertEquals(2, messageIds.size());
		assertEquals(FIRST_ID, messageIdList.get(0).getWithAngleBrackets());
		assertEquals(SECOND_ID, messageIdList.get(1).getWithAngleBrackets());
	}

	@Test
	public void testExctractAllWithCommaSeparation() {

		EmailMessageIdExtractor extractor = createExtractor(FIRST_ID + "," + SECOND_ID);

		Collection<EmailMessageId> messageIds = extractor.extractAll();

		ArrayList<EmailMessageId> messageIdList = new ArrayList<>(messageIds);
		assertEquals(2, messageIds.size());
		assertEquals(FIRST_ID, messageIdList.get(0).getWithAngleBrackets());
		assertEquals(SECOND_ID, messageIdList.get(1).getWithAngleBrackets());
	}

	@Test
	public void testExctractAllWithCrippledId() {

		EmailMessageIdExtractor extractor = createExtractor(ID_WITHOUT_CLOSING_BRACKET + " " + SECOND_ID);

		Collection<EmailMessageId> messageIds = extractor.extractAll();

		ArrayList<EmailMessageId> messageIdList = new ArrayList<>(messageIds);
		assertEquals(2, messageIds.size());
		assertEquals(ID_WITHOUT_CLOSING_BRACKET + ">", messageIdList.get(0).getWithAngleBrackets());
		assertEquals(SECOND_ID, messageIdList.get(1).getWithAngleBrackets());
	}

	private EmailMessageIdExtractor createExtractor(String input) {

		return new EmailMessageIdExtractor(input);
	}
}
