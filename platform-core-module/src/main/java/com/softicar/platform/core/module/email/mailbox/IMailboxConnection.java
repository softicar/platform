package com.softicar.platform.core.module.email.mailbox;

import com.softicar.platform.core.module.server.AGServer;
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
	 * @param folder
	 *            the folder name or path (never <i>null</i>)
	 * @return the contained {@link IMailboxMessage} objects
	 */
	Collection<IMailboxMessage> getMessagesInFolder(String folder);
}
