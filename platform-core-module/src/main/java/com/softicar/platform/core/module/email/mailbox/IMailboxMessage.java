package com.softicar.platform.core.module.email.mailbox;

import java.io.OutputStream;
import java.util.Collection;

public interface IMailboxMessage {

	/**
	 * Returns the subject of this {@link IMailboxMessage}.
	 *
	 * @return the subject (never <i>null</i>)
	 */
	String getSubject();

	/**
	 * Returns the <i>FROM</i> addresses of this {@link IMailboxMessage}.
	 *
	 * @return the <i>FROM</i> addresses (never <i>null</i>)
	 */
	Collection<String> getFrom();

	/**
	 * Returns all <i>TO</i> recipients of this {@link IMailboxMessage}.
	 *
	 * @return all <i>TO</i> recipients
	 */
	Collection<String> getToRecipients();

	/**
	 * Returns all <i>CC</i> recipients of this {@link IMailboxMessage}.
	 *
	 * @return all <i>CC</i> recipients
	 */
	Collection<String> getCcRecipients();

	/**
	 * Returns all <i>BCC</i> recipients of this {@link IMailboxMessage}.
	 *
	 * @return all <i>BCC</i> recipients
	 */
	Collection<String> getBccRecipients();

	/**
	 * Returns all recipients of this {@link IMailboxMessage}.
	 * <p>
	 * This includes all <i>TO</i>, <i>CC</i> and <i>BCC</i> recipients.
	 *
	 * @return all recipients (never <i>null</i>)
	 */
	Collection<String> getAllRecipients();

	/**
	 * Returns the content object of this {@link IMailboxMessage}.
	 * <p>
	 * TODO improve API
	 *
	 * @return the content object (never <i>null</i>)
	 */
	Object getContent();

	/**
	 * Writes this {@link IMailboxMessage} to the given {@link OutputStream}.
	 *
	 * @param outputStream
	 *            the {@link OutputStream} (never <i>null</i>)
	 */
	void writeTo(OutputStream outputStream);

	/**
	 * Copies this message to the given folder.
	 *
	 * @param folder
	 *            the target folder name (never <i>null</i>)
	 */
	void copyTo(String folder);

	/**
	 * Moves this message to the given folder.
	 *
	 * @param folder
	 *            the target folder name (never <i>null</i>)
	 */
	void moveTo(String folder);
}
