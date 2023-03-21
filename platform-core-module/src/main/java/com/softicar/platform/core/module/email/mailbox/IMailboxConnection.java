package com.softicar.platform.core.module.email.mailbox;

import com.softicar.platform.core.module.server.AGServer;
import jakarta.mail.Message;
import java.util.Collection;

/**
 * A connection to a mailbox.
 *
 * @author Oliver Richers
 */
public interface IMailboxConnection extends AutoCloseable {

	/**
	 * Creates a new {@link IMailboxConnection} to the mailbox as defined by the
	 * given {@link AGServer}.
	 *
	 * @param server
	 *            a {@link AGServer} (never <i>null</i>)
	 * @return the new {@link IMailboxConnection} (never <i>null</i>)
	 */
	static IMailboxConnection getInstance(AGServer server) {

		return IMailboxConnector.getInstance(server).connectTo(server);
	}

	/**
	 * Closes all resources opened by this {@link IMailboxConnection}.
	 */
	@Override
	void close();

	/**
	 * Returns a {@link Collection} of all messages contained in the given
	 * mailbox folder.
	 *
	 * @param folderName
	 *            the folder name or path (never <i>null</i>)
	 * @return the contained {@link IMailboxMessage} objects
	 */
	Collection<IMailboxMessage> getMessagesInFolder(String folderName);

	/**
	 * Copies the given {@link Message} to the given mailbox folder.
	 *
	 * @param message
	 *            the message to copy (never <i>null</i>)
	 * @param targetFolderName
	 *            the target folder name or path (never <i>null</i>)
	 */
	void copyMessageTo(Message message, String targetFolderName);

	/**
	 * Moves the given {@link Message} to the given mailbox folder.
	 *
	 * @param message
	 *            the message to move (never <i>null</i>)
	 * @param targetFolderName
	 *            the target folder name or path (never <i>null</i>)
	 */
	void moveMessageTo(Message message, String targetFolderName);
}
