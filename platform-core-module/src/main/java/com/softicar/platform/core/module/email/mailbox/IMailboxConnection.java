package com.softicar.platform.core.module.email.mailbox;

import com.softicar.platform.core.module.server.AGServer;
import java.util.Collection;

public interface IMailboxConnection extends AutoCloseable {

	static IMailboxConnection getInstance(AGServer server) {

		return IMailboxConnector.getInstance(server).connectTo(server);
	}

	/**
	 * Closes all resources opened by this {@link IMailboxConnection}.
	 */
	@Override
	void close();

	Collection<IMailboxMessage> getMessagesInFolder(String folder);
}
