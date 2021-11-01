package com.softicar.platform.core.module.email.message;

import java.util.Objects;
import javax.mail.Message;
import javax.mail.MessagingException;

public class EmailMessageId implements Comparable<EmailMessageId> {

	private static final String HEADER_NAME = "Message-ID";
	private static final int MAXIMUM_LENGTH = 255;
	private final String messageId;

	public EmailMessageId(Message message) {

		this(getMessageId(message));
	}

	public EmailMessageId(String messageId) {

		if (messageId == null || messageId.isEmpty()) {
			throw new IllegalEmailMessageIdException("Missing message IDs in email.");
		} else if (!messageId.startsWith("<") || !messageId.endsWith(">")) {
			messageId = '<' + messageId + '>';
		}

		this.messageId = messageId.substring(1, messageId.length() - 1);

		validate();
	}

	private void validate() {

		if (this.messageId.isEmpty()) {
			throw new IllegalEmailMessageIdException("Missing message IDs in email.");
		} else if (messageId.length() > MAXIMUM_LENGTH) {
			throw new IllegalEmailMessageIdException("Message-ID is too long: %s > %s", messageId.length(), MAXIMUM_LENGTH);
		}
	}

	public String getWithoutAngleBrackets() {

		return messageId;
	}

	public String getWithAngleBrackets() {

		return '<' + messageId + '>';
	}

	@Override
	public String toString() {

		return getWithAngleBrackets();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof EmailMessageId) {
			return Objects.equals(messageId, ((EmailMessageId) object).messageId);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return messageId.hashCode();
	}

	@Override
	public int compareTo(EmailMessageId other) {

		return messageId.compareTo(other.messageId);
	}

	private static String getMessageId(Message message) {

		try {
			String[] values = message.getHeader(HEADER_NAME);
			if (values != null && values.length == 1) {
				return values[0];
			} else if (values == null || values.length < 1) {
				throw new IllegalEmailMessageIdException("Missing message IDs in email.");
			} else {
				throw new IllegalEmailMessageIdException("Multiple message IDs in email.");
			}
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}
}
