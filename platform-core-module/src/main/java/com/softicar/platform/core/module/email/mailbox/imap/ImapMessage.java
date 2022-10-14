package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.core.module.email.mailbox.IMailboxMessage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage.RecipientType;

class ImapMessage implements IMailboxMessage {

	private final ImapConnection connection;
	private final Message message;

	public ImapMessage(ImapConnection connection, Message message) {

		this.connection = connection;
		this.message = message;
	}

	@Override
	public String getSubject() {

		try {
			return message.getSubject();
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

		connection.copyMessageTo(message, folder);
	}

	@Override
	public void moveTo(String folder) {

		connection.moveMessageTo(message, folder);
	}

	private Collection<String> toStrings(Address[] addresses) {

		return addresses != null? Stream//
			.of(addresses)
			.map(Address::toString)
			.collect(Collectors.toList()) : Collections.emptyList();
	}
}
