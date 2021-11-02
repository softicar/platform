package com.softicar.platform.core.module.email;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.core.module.email.message.EmailMessageId;
import com.softicar.platform.core.module.email.transport.EmailTransport;
import com.softicar.platform.core.module.user.AGUser;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class Email implements IEmail {

	private final EmailTransport transport;
	private final MimeMessage mailMessage;
	private final MimeMultipart multiPart;

	private List<Address> getAllRecipientsList() {

		List<Address> addressesList = new ArrayList<>();

		try {
			if (mailMessage.getRecipients(RecipientType.TO) != null) {
				for (Address address: mailMessage.getRecipients(RecipientType.TO)) {
					addressesList.add(address);
				}
			}
			if (mailMessage.getRecipients(RecipientType.CC) != null) {
				for (Address address: mailMessage.getRecipients(RecipientType.CC)) {
					addressesList.add(address);
				}
			}
			if (mailMessage.getRecipients(RecipientType.BCC) != null) {
				for (Address address: mailMessage.getRecipients(RecipientType.BCC)) {
					addressesList.add(address);
				}
			}
		} catch (MessagingException e) {
			throw new SofticarException(e);
		}

		return addressesList;
	}

	public Email(Session session, EmailTransport transport, String fromAddressString) {

		this.transport = transport;
		this.mailMessage = new MimeMessage(session);
		this.multiPart = new MimeMultipart();

		setFrom(fromAddressString);
	}

	@Override
	public IEmail setFrom(String sender) {

		try {
			mailMessage.setFrom(parseAddress(sender));
		} catch (AddressException e) {
			throw new SofticarException(e);
		} catch (MessagingException e) {
			throw new SofticarException(e);
		}
		return this;
	}

	@Override
	public IEmail setSender(String sender) {

		try {
			mailMessage.setSender(parseAddress(sender));
		} catch (AddressException e) {
			throw new SofticarException(e);
		} catch (MessagingException e) {
			throw new SofticarException(e);
		}
		return this;
	}

	@Override
	public IEmail setReplyTo(String recipient) {

		try {
			mailMessage.setReplyTo(new Address[] { parseAddress(recipient) });
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
		return this;
	}

	@Override
	public IEmail addToRecipient(String recipient) {

		try {
			mailMessage.addRecipient(RecipientType.TO, parseAddress(recipient));
		} catch (MessagingException e) {
			throw new SofticarException(e);
		}
		return this;
	}

	@Override
	public IEmail addCcRecipient(String recipient) {

		try {
			mailMessage.addRecipient(RecipientType.CC, parseAddress(recipient));
		} catch (MessagingException e) {
			throw new SofticarException(e);
		}
		return this;
	}

	@Override
	public IEmail addBccRecipient(String recipient) {

		try {
			mailMessage.addRecipient(RecipientType.BCC, parseAddress(recipient));
		} catch (MessagingException e) {
			throw new SofticarException(e);
		}
		return this;
	}

	@Override
	public IEmail setMessageId(EmailMessageId messageId) {

		try {
			mailMessage.setHeader("Message-ID", messageId.getWithAngleBrackets());
		} catch (MessagingException exception) {
			throw new SofticarException(exception);
		}
		return this;
	}

	@Override
	public IEmail setInReplyTo(EmailMessageId inReplyTo) {

		try {
			mailMessage.setHeader("In-Reply-To", inReplyTo.getWithAngleBrackets());
		} catch (MessagingException exception) {
			throw new SofticarException(exception);
		}
		return this;
	}

	@Override
	public IEmail addReference(EmailMessageId referencedMessageId) {

		try {
			mailMessage.addHeader("References", referencedMessageId.getWithAngleBrackets());
		} catch (MessagingException exception) {
			throw new SofticarException(exception);
		}
		return this;
	}

	@Override
	public IEmail addReferences(Collection<EmailMessageId> referencedMessageIds) {

		referencedMessageIds.forEach(this::addReference);
		return this;
	}

	@Override
	public IEmail setContent(String content, EmailContentType contentType) {

		try {
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(content, contentType.getContentTypeString());
			multiPart.addBodyPart(bodyPart);
		} catch (MessagingException e) {
			throw new SofticarException(e);
		}
		return this;
	}

	@Override
	public IEmail setAutoSubmitted(String autoSubmitted) {

		try {
			mailMessage.setHeader("Auto-Submitted", autoSubmitted);
		} catch (MessagingException exception) {
			throw new SofticarException(exception);
		}
		return this;
	}

	@Override
	public IEmail addFile(File file) {

		BodyPart bodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file.getAbsoluteFile());
		try {
			bodyPart.setDataHandler(new DataHandler(source));
			bodyPart.setFileName(file.getName());
			multiPart.addBodyPart(bodyPart);
		} catch (MessagingException e) {
			throw new SofticarException(e);
		}
		return this;
	}

	@Override
	public IEmail addByteArray(byte[] byteArray, String name, IMimeType mimeType) {

		return addData(new ByteArrayDataSource(byteArray, mimeType.getIdentifier()), name);
	}

	@Override
	public IEmail addStream(InputStream inputStream, String name, IMimeType mimeType) {

		try {
			return addData(new ByteArrayDataSource(inputStream, mimeType.getIdentifier()), name);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public IEmail setSubject(IDisplayString subject) {

		try {
			mailMessage.setSubject(subject.toString(), "utf-8");
		} catch (MessagingException e) {
			throw new SofticarException(e);
		}
		return this;
	}

	@Override
	public void submit() {

		// finish e-mail
		try {
			mailMessage.setContent(multiPart);
		} catch (MessagingException e1) {
			throw new SofticarException(e1);
		}

		// e-mail redirection
		List<Address> recipients = getAllRecipientsList();
		String redirectionAddress = EmailSystemProperties.REDIRECTION_ADDRESS.getValue();
		if (redirectionAddress != null) {
			Address address = parseAddress(redirectionAddress);
			recipients = new ArrayList<>();
			recipients.add(address);
		}

		// send e-mail
		if (EmailSystemProperties.SENDING_ENABLED.getValue()) {
			transport.sendMessage(mailMessage, recipients);
		}

		// dump e-mail
		if (EmailSystemProperties.DUMPING_ENABLED.getValue()) {
			new EmailDumper(mailMessage).dump();
		}
	}

	private InternetAddress parseAddress(String redirectionAddress) {

		try {
			return new InternetAddress(redirectionAddress);
		} catch (AddressException exception) {
			throw new SofticarException(exception);
		}
	}

	private IEmail addData(DataSource source, String name) {

		BodyPart bodyPart = new MimeBodyPart();
		try {
			bodyPart.setDataHandler(new DataHandler(source));
			bodyPart.setFileName(name);
			multiPart.addBodyPart(bodyPart);
		} catch (MessagingException exception) {
			throw new SofticarException(exception);
		}
		return this;
	}

	@Override
	public IEmail setFrom(AGUser from) {

		return setFrom(from.getEmailAddress());
	}

	@Override
	public IEmail setSender(AGUser sender) {

		return setSender(sender.getEmailAddress());
	}

	@Override
	public IEmail setReplyTo(AGUser replyTo) {

		return setReplyTo(replyTo.getEmailAddress());
	}

	@Override
	public IEmail addCcRecipient(AGUser recipient) {

		return addCcRecipient(recipient.getEmailAddress());
	}

	@Override
	public IEmail addBccRecipient(AGUser recipient) {

		// TODO Auto-generated method stub
		return null;
	}
}
