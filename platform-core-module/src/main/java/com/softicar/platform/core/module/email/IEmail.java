package com.softicar.platform.core.module.email;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.core.module.email.message.EmailMessageId;
import com.softicar.platform.core.module.email.recipient.EmailRecipient;
import com.softicar.platform.core.module.user.AGUser;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;

/**
 * Represents an e-mail object according to RFC2822.
 *
 * @see <a href="https://tools.ietf.org/html/rfc2822">RFC2822</a>
 * @author Oliver Richers
 */
public interface IEmail {

	/**
	 * Sets the from header of this e-mail.
	 *
	 * @param from
	 *            the from address
	 * @return this e-mail object
	 */
	IEmail setFrom(String from);

	IEmail setFrom(AGUser from);

	/**
	 * Sets the sender header of this e-mail.
	 *
	 * @param sender
	 *            the sender address
	 * @return this e-mail object
	 */
	IEmail setSender(String sender);

	IEmail setSender(AGUser sender);

	/**
	 * Sets the reply-to header of this e-mail.
	 *
	 * @param replyTo
	 *            the reply-to address
	 * @return this e-mail object
	 */
	IEmail setReplyTo(String replyTo);

	IEmail setReplyTo(AGUser replyTo);

	/**
	 * Adds the given user to-recipients.
	 *
	 * @param recipient
	 *            the recipients
	 * @return this e-mail object
	 */
	default IEmail addToRecipient(AGUser recipient) {

		addToRecipient(recipient.getEmailAddress());
		return this;
	}

	/**
	 * Adds the given users as to-recipients.
	 *
	 * @param recipients
	 *            the recipients
	 * @return this e-mail object
	 */
	default IEmail addToRecipientUsers(Collection<AGUser> recipients) {

		recipients.forEach(user -> addToRecipient(user));
		return this;
	}

	/**
	 * Adds the given to-recipients.
	 *
	 * @param recipients
	 *            the recipients
	 * @return this e-mail object
	 */
	default IEmail addToRecipients(Collection<String> recipients) {

		recipients.forEach(this::addToRecipient);
		return this;
	}

	default IEmail addAGUsersToRecipients(Collection<AGUser> recipients) {

		recipients.forEach(this::addToRecipient);
		return this;
	}

	/**
	 * Adds the given recipients.
	 *
	 * @param recipients
	 *            the recipients
	 * @return this e-mail object
	 */
	default IEmail addRecipients(Collection<? extends EmailRecipient> recipients) {

		recipients.forEach(this::addRecipient);
		return this;
	}

	/**
	 * Adds the given recipient.
	 *
	 * @param recipient
	 *            the recipient
	 * @return this e-mail object
	 */
	default IEmail addRecipient(EmailRecipient recipient) {

		switch (recipient.getRecipientType()) {
		case TO:
			addToRecipient(recipient.getAddress());
			break;
		case CC:
			addCcRecipient(recipient.getAddress());
			break;
		case BCC:
			addBccRecipient(recipient.getAddress());
			break;
		}
		return this;
	}

	/**
	 * Adds the given to-recipient.
	 *
	 * @param recipient
	 *            the recipient
	 * @return this e-mail object
	 */
	IEmail addToRecipient(String recipient);

	/**
	 * Adds the given carbon-copy recipient.
	 *
	 * @param recipient
	 *            the recipient
	 * @return this e-mail object
	 */
	IEmail addCcRecipient(String recipient);

	IEmail addCcRecipient(AGUser recipient);

	/**
	 * Adds the given blind-carbon-copy recipient.
	 *
	 * @param recipient
	 *            the recipient
	 * @return this e-mail object
	 */
	IEmail addBccRecipient(String recipient);

	IEmail addBccRecipient(AGUser recipient);

	/**
	 * Sets the message identifier of this e-mail.
	 *
	 * @param messageId
	 *            the message identifier
	 * @return this e-mail object
	 */
	IEmail setMessageId(EmailMessageId messageId);

	/**
	 * Sets the in-reply-to message identifier.
	 *
	 * @param inReplyTo
	 *            the in-reply-to message identifier
	 * @return this e-mail object
	 */
	IEmail setInReplyTo(EmailMessageId inReplyTo);

	/**
	 * Adds the referenced message identifier.
	 *
	 * @param referencedMessageId
	 *            the referenced message identifier
	 * @return this e-mail object
	 */
	IEmail addReference(EmailMessageId referencedMessageId);

	/**
	 * Adds the referenced message identifiers.
	 *
	 * @param referencedMessageIds
	 *            the referenced message identifiers
	 * @return this e-mail object
	 */
	IEmail addReferences(Collection<EmailMessageId> referencedMessageIds);

	/**
	 * Sets the subject of this e-mail.
	 *
	 * @param subject
	 *            the subject (never null)
	 * @return this e-mail object
	 */
	IEmail setSubject(IDisplayString subject);

	/**
	 * Sets the content of this e-mail to the given plain-text.
	 * <p>
	 * This is a convenience method for {@link #setContent}.
	 *
	 * @param plainText
	 *            the plain-text content
	 * @return this e-mail object
	 */
	default IEmail setPlainTextContent(String plainText) {

		return setContent(plainText, EmailContentType.PLAIN);
	}

	/**
	 * Sets the textual content of this e-mail.
	 *
	 * @param content
	 *            the content text
	 * @param contentType
	 *            the content type
	 * @return this e-mail object
	 */
	IEmail setContent(String content, EmailContentType contentType);

	/**
	 * Sets the auto-submitted header of the email.
	 *
	 * @param autoSubmitted
	 *            the auto-submitted header value (may be null to skip the
	 *            header)
	 * @return this e-mail object
	 */
	IEmail setAutoSubmitted(String autoSubmitted);

	/**
	 * Adds the given file as attachment to this e-mail.
	 * <p>
	 * The use of this method is discourage because it does not specify a
	 * mime-type. Furthermore, referencing a file directly from a Java program
	 * is usually a bad idea. Use {@link #addByteArray} or {@link #addStream}
	 * instead.
	 *
	 * @param file
	 *            the file to add
	 * @return this e-mail object
	 */
	IEmail addFile(File file);

	/**
	 * Adds the given byte array as attachment to this e-mail.
	 *
	 * @param byteArray
	 *            the byte array
	 * @param name
	 *            the attachment name
	 * @param mimeType
	 *            the attachment mime-type
	 * @return this e-mail object
	 */
	IEmail addByteArray(byte[] byteArray, String name, IMimeType mimeType);

	/**
	 * Adds the content of the given {@link InputStream} as attachment to this
	 * e-mail.
	 *
	 * @param inputStream
	 *            the input stream providing the data
	 * @param name
	 *            the attachment name
	 * @param mimeType
	 *            the attachment mime-type
	 * @return this e-mail object
	 */
	IEmail addStream(InputStream inputStream, String name, IMimeType mimeType);

	/**
	 * Submits this e-mail.
	 * <p>
	 * This method sends this e-mail immediately or registers it for sending
	 * later.
	 */
	void submit();

	/**
	 * Converts this {@link IEmail} into a {@link MimeMessage}.
	 *
	 * @return the {@link MimeMessage} (never <i>null</i>)
	 */
	MimeMessage toMimeMessage();
}
