package com.softicar.platform.core.module.email.mailbox;

import com.softicar.platform.core.module.email.mailbox.imap.ImapConnector;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.server.connector.IServerConnector;
import java.util.Properties;

/**
 * An {@link IServerConnector} to access a mailbox.
 *
 * @author Oliver Richers
 */
public interface IMailboxConnector extends IServerConnector {

	static IMailboxConnector getInstance(AGServer server) {

		return (IMailboxConnector) server.getConnector().orElse(new ImapConnector());
	}

	/**
	 * Connects to the mailbox on the given {@link AGServer}.
	 *
	 * @param server
	 *            the {@link AGServer} (never <i>null</i>)
	 * @param additionalProperties
	 *            additional connection properties (never <i>null</i>)
	 * @return the new {@link IMailboxConnection} to access the mailbox
	 */
	IMailboxConnection connectTo(AGServer server, Properties additionalProperties);

	/**
	 * Same as {@link #connectTo(AGServer, Properties)} but with no additional
	 * properties.
	 *
	 * @param server
	 *            the {@link AGServer} (never <i>null</i>)
	 * @return the new {@link IMailboxConnection} to access the mailbox
	 */
	default IMailboxConnection connectTo(AGServer server) {

		return connectTo(server, new Properties());
	}
}
