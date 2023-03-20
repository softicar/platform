package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.core.module.email.mailbox.IMailboxConnection;
import com.softicar.platform.core.module.email.mailbox.IMailboxMessage;
import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO EXP-2769 Should be renamed to "MailboxMessage", and moved to another
// package. There is nothing IMAP specific in here.
public class ImapMessage implements IMailboxMessage {

	private final IMailboxConnection connection;
	private final Message message;

	public ImapMessage(Message message) {

		this(null, message);
	}

	public ImapMessage(IMailboxConnection connection, Message message) {

		this.connection = connection;
		this.message = message;
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
