package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.mailbox.IMailboxConnection;
import com.softicar.platform.core.module.email.mailbox.IMailboxConnector;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.server.connector.IServerConnectorConfigurationInput;
import com.softicar.platform.dom.node.IDomNode;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

@SourceCodeReferencePointUuid("d4992229-1a91-4b51-abc8-f44440a89070")
public class ImapConnector implements IMailboxConnector {

	@Override
	public IDisplayString toDisplay() {

		return CoreI18n.DEFAULT_IMAP_CONNECTOR;
	}

	@Override
	public IDomNode createConfigurationDisplay(AGServer server) {

		return new ImapConfigurationDisplay(server);
	}

	@Override
	public IServerConnectorConfigurationInput createConfigurationInput(AGServer server) {

		return new ImapConfigurationInput();
	}

	@Override
	public IMailboxConnection connectTo(AGServer server) {

		return new ImapConnection(() -> createStore(server));
	}

	private Store createStore(AGServer server) {

		try {
			var properties = ImapConfiguration.fromJson(server.getConnectorConfiguration()).getProperties();
			var store = Session.getInstance(properties).getStore();
			store.connect(server.getAddress(), server.getUsername(), server.getPassword());
			return store;
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}
}
