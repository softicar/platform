package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.email.EmailDumper;
import com.softicar.platform.core.module.email.EmailSystemProperties;
import com.softicar.platform.core.module.email.buffer.attachment.AGBufferedEmailAttachment;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.db.core.transaction.DbTransaction;
import jakarta.activation.DataHandler;
import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

/**
 * Sends all e-mails in {@link AGBufferedEmail} that have not been sent yet.
 *
 * @author Oliver Richers
 */
public class BufferedEmailSender {

	private final BufferedEmailSenderSessionManager sessionManager;
	private final ExceptionsCollector exceptionsCollector;
	private final AGServer emailServer;

	public BufferedEmailSender(AGServer emailServer) {

		this.emailServer = emailServer;
		this.sessionManager = new BufferedEmailSenderSessionManager();
		this.exceptionsCollector = new ExceptionsCollector();
	}

	public void sendAll() {

		Log.finfo("Sending e-mails...");
		AGBufferedEmail.TABLE//
			.createSelect()
			.where(AGBufferedEmail.ACTIVE)
			.where(AGBufferedEmail.SENT_AT.isNull())
			.where(AGBufferedEmail.EMAIL_SERVER.isEqual(emailServer))
			.orderBy(AGBufferedEmail.ID)
			.forEach(this::send);
		Log.finfo("done");
		exceptionsCollector.throwExceptionIfNotEmpty();
	}

	private void send(AGBufferedEmail email) {

		try (DbTransaction transaction = new DbTransaction()) {
			email.reloadForUpdate();
			if (email.getSentAt() == null) {
				MimeMessage mimeMessage = createMimeMessage(email);
				// send email
				if (EmailSystemProperties.SENDING_ENABLED.getValue()) {
					Transport.send(mimeMessage);
					Log.finfo("Sent email #%s.", email.getId());
				}
				// dump email
				if (EmailSystemProperties.DUMPING_ENABLED.getValue()) {
					new EmailDumper(mimeMessage).dump();
					Log.finfo("Dumped email #%s.", email.getId());
				}
			}
			email.setSentAt(DayTime.now());
			email.save();
			transaction.commit();
		} catch (Exception exception) {
			exceptionsCollector.add(exception);
		}
	}

	private MimeMessage createMimeMessage(AGBufferedEmail email) throws MessagingException {

		Session session = sessionManager.getSession(emailServer);
		CustomMimeMessage message = new CustomMimeMessage(session, email);

		// set addresses
		parseAndSetAddresses(email.getFrom(), message::addFrom);
		parseAndSetAddresses(email.getSender(), sender -> message.setSender(sender[0]));
		parseAndSetAddresses(email.getReplyTo(), message::setReplyTo);
		parseAndSetAddresses(email.getTo(), addresses -> message.addRecipients(RecipientType.TO, addresses));
		parseAndSetAddresses(email.getCc(), addresses -> message.addRecipients(RecipientType.CC, addresses));
		parseAndSetAddresses(email.getBcc(), addresses -> message.addRecipients(RecipientType.BCC, addresses));

		// set message identifiers and other headers
		setHeaderIfPresent(message, "Message-ID", email.getMessageId());
		setHeaderIfPresent(message, "In-Reply-To", email.getInReplyTo());
		setHeaderIfPresent(message, "References", email.getReferences());
		setHeaderIfPresent(message, "Auto-Submitted", email.getAutoSubmitted());

		// set subject
		message.setSubject(email.getSubject());

		// create message body
		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(createBodyPart(email.getContent(), email.getContentType()));
		for (AGBufferedEmailAttachment attachment: AGBufferedEmailAttachment.TABLE//
			.createSelect()
			.where(AGBufferedEmailAttachment.EMAIL.isEqual(email))
			.orderBy(AGBufferedEmailAttachment.INDEX)) {
			multipart.addBodyPart(createBodyPart(attachment));
		}
		message.setContent(multipart);

		return message;
	}

	private static void parseAndSetAddresses(String addresses, AddressesSetter setter) {

		if (addresses != null && !addresses.trim().isEmpty()) {
			try {
				setter.accept(BufferedEmailAddresses.parseAddresses(addresses));
			} catch (MessagingException exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	private void setHeaderIfPresent(MimeMessage message, String headerName, String headerValue) throws MessagingException {

		if (headerValue != null && !headerValue.trim().isEmpty()) {
			message.setHeader(headerName, headerValue);
		}
	}

	private BodyPart createBodyPart(String content, String contentType) throws MessagingException {

		BodyPart part = new MimeBodyPart();
		part.setContent(content, contentType);
		part.setDisposition("inline");
		return part;
	}

	private BodyPart createBodyPart(AGBufferedEmailAttachment attachment) throws MessagingException {

		ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment.getData(), attachment.getType());

		BodyPart part = new MimeBodyPart();
		part.setDataHandler(new DataHandler(dataSource));
		part.setFileName(attachment.getName());
		part.setDisposition("attachment");
		return part;
	}

	@FunctionalInterface
	private static interface AddressesSetter {

		void accept(Address[] addresses) throws MessagingException;
	}

	private class CustomMimeMessage extends MimeMessage {

		private final AGBufferedEmail email;

		public CustomMimeMessage(Session session, AGBufferedEmail email) {

			super(session);
			this.email = email;
		}

		@Override
		protected void updateMessageID() throws MessagingException {

			if (email.getMessageId() == null) {
				super.updateMessageID();
			}
		}
	}
}
