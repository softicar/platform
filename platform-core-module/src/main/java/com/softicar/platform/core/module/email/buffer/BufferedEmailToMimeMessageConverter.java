package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.email.buffer.attachment.BufferedEmailAttachment;
import com.softicar.platform.core.module.email.message.EmailMessageId;
import jakarta.activation.DataHandler;
import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

public class BufferedEmailToMimeMessageConverter {

	private final BufferedEmail email;

	public BufferedEmailToMimeMessageConverter(BufferedEmail email) {

		this.email = email;
	}

	public MimeMessage convert() {

		try {
			var message = new MimeMessage((Session) null);

			// set addresses
			if (email.from != null) {
				message.setFrom(email.from);
			}
			if (email.sender != null) {
				message.setSender(email.sender);
			}
			if (email.replyTo != null) {
				message.setReplyTo(new Address[] { email.replyTo });
			}
			message.setRecipients(RecipientType.TO, email.toAddresses.toArray(new Address[0]));
			message.setRecipients(RecipientType.CC, email.ccAddresses.toArray(new Address[0]));
			message.setRecipients(RecipientType.BCC, email.bccAddresses.toArray(new Address[0]));

			// set message identifiers and other headers
			setHeaderIfPresent(message, "Message-ID", email.messageId != null? email.messageId.toString() : "");
			setHeaderIfPresent(message, "In-Reply-To", email.inReplyTo != null? email.inReplyTo.toString() : "");
			setHeaderIfPresent(message, "References", Imploder.implode(email.references, EmailMessageId::getWithAngleBrackets, " "));
			setHeaderIfPresent(message, "Auto-Submitted", email.autoSubmitted);

			// set subject
			message.setSubject(email.subject);

			// create message body
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(createInlineBodyPart());
			for (BufferedEmailAttachment attachment: email.attachments) {
				multipart.addBodyPart(createAttachmentBodyPart(attachment));
			}
			message.setContent(multipart);

			return message;
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void setHeaderIfPresent(MimeMessage message, String headerName, String headerValue) throws MessagingException {

		if (headerValue != null && !headerValue.isBlank()) {
			message.setHeader(headerName, headerValue);
		}
	}

	private BodyPart createInlineBodyPart() throws MessagingException {

		var part = new MimeBodyPart();
		part.setContent(email.content, email.contentType.getContentTypeString());
		part.setDisposition("inline");
		return part;
	}

	private BodyPart createAttachmentBodyPart(BufferedEmailAttachment attachment) throws MessagingException {

		ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment.getData(), attachment.getType().getIdentifier());

		BodyPart part = new MimeBodyPart();
		part.setDataHandler(new DataHandler(dataSource));
		part.setFileName(attachment.getName());
		part.setDisposition("attachment");
		return part;
	}
}
