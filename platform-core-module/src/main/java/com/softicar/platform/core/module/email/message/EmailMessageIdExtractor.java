package com.softicar.platform.core.module.email.message;

import java.util.ArrayList;
import java.util.Collection;

public class EmailMessageIdExtractor {

	private static final String SEPARATOR_REGEX = "[<>\\s,]";
	private final String input;

	public EmailMessageIdExtractor(String input) {

		this.input = input;
	}

	public Collection<EmailMessageId> extractAll() {

		Collection<EmailMessageId> messageIds = new ArrayList<>();
		for (String token: input.split(SEPARATOR_REGEX)) {
			if (!token.isEmpty()) {
				messageIds.add(new EmailMessageId("<" + token + ">"));
			}
		}
		return messageIds;
	}
}
