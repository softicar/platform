package com.softicar.platform.core.module.email.mailbox;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A convenient wrapper around {@link Message}.
 *
 * @author Alexander Schmidt
 */
public class MailboxMessage implements IMailboxMessage {

	private final Message message;
	private final IMailboxConnection connection;

	/**
	 * Creates a {@link MailboxMessage} from the given {@link Message}, without
	 * an {@link IMailboxConnection}.
	 * <p>
	 * Subsequent calls to {@link #copyTo(String)} and {@link #moveTo(String)}
	 * will result in an {@link UnsupportedOperationException}.
	 *
	 * @param message
	 *            the message (never <i>null</i>)
	 */
	public MailboxMessage(Message message) {

		this(message, null);
	}

	/**
	 * Creates a {@link MailboxMessage} from the given {@link Message}, using
	 * the given {@link IMailboxConnection}.
	 * <p>
	 * If the given {@link IMailboxConnection} is <i>null</i>, subsequent calls
	 * to {@link #copyTo(String)} and {@link #moveTo(String)} will result in an
	 * {@link UnsupportedOperationException}.
	 *
	 * @param message
	 *            the message (never <i>null</i>)
	 * @param connection
	 *            the connection to the server on which the message is stored
	 *            (may be <i>null</i>)
	 */
	public MailboxMessage(Message message, IMailboxConnection connection) {

		this.message = Objects.requireNonNull(message);
		this.connection = connection;
	}

	@Override
	public String getSubject() {

		try {
			return Optional.ofNullable(message.getSubject()).orElse("");
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Collection<String> getFrom() {

		try {
			return toStrings(message.getFrom());
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Collection<String> getToRecipients() {

		try {
			return toStrings(message.getRecipients(RecipientType.TO));
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Collection<String> getCcRecipients() {

		try {
			return toStrings(message.getRecipients(RecipientType.CC));
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Collection<String> getBccRecipients() {

		try {
			return toStrings(message.getRecipients(RecipientType.BCC));
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Collection<String> getAllRecipients() {

		try {
			return toStrings(message.getAllRecipients());
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Collection<String> getHeader(String headerName) {

		try {
			return Optional//
				.ofNullable(message.getHeader(headerName))
				.map(Arrays::asList)
				.orElse(Collections.emptyList());
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Object getContent() {

		try {
			return message.getContent();
		} catch (IOException | MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void writeTo(OutputStream outputStream) {

		try {
			message.writeTo(outputStream);
		} catch (IOException | MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void copyTo(String folder) {

		if (connection == null) {
			throw new UnsupportedOperationException("No connection available.");
		}
		connection.copyMessageTo(message, folder);
	}

	@Override
	public void moveTo(String folder) {

		if (connection == null) {
			throw new UnsupportedOperationException("No connection available.");
		}
		connection.moveMessageTo(message, folder);
	}

	private Collection<String> toStrings(Address[] addresses) {

		return addresses != null? Stream//
			.of(addresses)
			.map(Address::toString)
			.collect(Collectors.toList()) : Collections.emptyList();
	}
}
