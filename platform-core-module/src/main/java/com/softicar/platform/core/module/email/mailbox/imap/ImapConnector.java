package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.mailbox.IMailboxConnection;
import com.softicar.platform.core.module.email.mailbox.IMailboxConnector;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.server.connector.IServerConnectorConfigurationInput;
import com.softicar.platform.dom.node.IDomNode;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import java.util.Properties;

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
	public IMailboxConnection connectTo(AGServer server, Properties additionalProperties) {

		return new ImapConnection(() -> createStore(server, additionalProperties));
	}

	private Store createStore(AGServer server, Properties additionalProperties) {

		try {
			var store = Session.getInstance(getProperties(server, additionalProperties)).getStore();
			store.connect(server.getAddress(), server.getUsername(), server.getPassword());
			return store;
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Properties getProperties(AGServer server, Properties additionalProperties) {

		Properties properties = new Properties();
		properties.putAll(ImapConfiguration.fromJson(server.getConnectorConfiguration()).getProperties());
		properties.putAll(additionalProperties);
		return properties;
	}
}
