package com.softicar.platform.core.module.email.message;

import com.softicar.platform.common.core.exceptions.SofticarException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.ContentType;
import jakarta.mail.internet.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Some utility methods for email {@link Message}.
 * <p>
 * Most methods are only wrapper to hide the stupid checked exception signatures
 * of the mail API.
 *
 * @author Oliver Richers
 */
public class EmailMessageUtils {

	public static String getSubject(Message message) {

		try {
			return message.getSubject();
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static String[] getHeader(Message message, String headerName) {

		try {
			return message.getHeader(headerName);
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static EmailMessageId getMessageId(Message message) {

		return new EmailMessageId(message);
	}

	public static Optional<EmailMessageId> getInReplyToMessageId(Message message) {

		String[] inReplyTos = getHeader(message, "In-Reply-To");
		if (inReplyTos != null && inReplyTos.length > 0 && inReplyTos[0] != null && !inReplyTos[0].isEmpty()) {
			if (inReplyTos.length > 1) {
				throw new SofticarException("Multiple in-reply-to header values in email.");
			}
			try {
				return Optional.of(new EmailMessageId(inReplyTos[0]));
			} catch (IllegalEmailMessageIdException exception) {
				exception.printStackTrace();
				return Optional.empty();
			}
		} else {
			return Optional.empty();
		}
	}

	public static List<EmailMessageId> getReferencedMessageIds(Message message) {

		String[] references = getHeader(message, "References");
		return references != null? Arrays//
			.asList(references)
			.stream()
			.map(header -> new EmailMessageIdExtractor(header).extractAll())
			.flatMap(Collection::stream)
			.collect(Collectors.toList()) : Collections.emptyList();
	}

	public static boolean isAutoSubmittedMessage(Message message) {

		String[] header = getHeader(message, "Auto-Submitted");
		if (header != null && header.length > 0 && !Objects.equals(header[0], "no")) {
			return true;
		} else {
			return false;
		}
	}

	public static ContentType createContentType(String contentTypeString) {

		try {
			return new ContentType(contentTypeString);
		} catch (ParseException exception) {
			throw new RuntimeException(exception);
		}
	}
}
