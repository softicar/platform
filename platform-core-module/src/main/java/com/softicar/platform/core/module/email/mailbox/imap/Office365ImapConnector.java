package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.auth.microsoft.MicrosoftOnlineAuthorizer;
import com.softicar.platform.core.module.email.mailbox.IMailboxConnection;
import com.softicar.platform.core.module.email.mailbox.IMailboxConnector;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.server.connector.IServerConnectorConfigurationInput;
import com.softicar.platform.dom.node.IDomNode;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import java.util.Properties;

@SourceCodeReferencePointUuid("88d66637-d757-4f95-8eef-3683f41f32c4")
public class Office365ImapConnector implements IMailboxConnector {

	@Override
	public IDisplayString toDisplay() {

		return CoreI18n.OFFICE_365_IMAP_CONNECTOR;
	}

	@Override
	public IDomNode createConfigurationDisplay(AGServer server) {

		return new Office365ImapConfigurationDisplay(server);
	}

	@Override
	public IServerConnectorConfigurationInput createConfigurationInput(AGServer server) {

		return new Office365ImapConfigurationInput();
	}

	@Override
	public IMailboxConnection connectTo(AGServer server) {

		return new ImapConnection(() -> createStore(server));
	}

	private Store createStore(AGServer server) {

		try {
			return createStoreWithRetry(server);
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Store createStoreWithRetry(AGServer server) throws MessagingException {

		var configuration = Office365ImapConfiguration.fromJson(server.getConnectorConfiguration());
		try {
			return tryToCreateStore(server, configuration, getAccessToken(server, configuration));
		} catch (Exception exception) {
			DevNull.swallow(exception);
			renewAccessToken(server, configuration);
			return tryToCreateStore(server, configuration, getAccessToken(server, configuration));
		}
	}

	private Store tryToCreateStore(AGServer server, Office365ImapConfiguration configuration, String token) throws MessagingException {

		var store = Session.getInstance(getProperties(server, configuration)).getStore();
		store.connect(server.getAddress(), server.getUsername(), token);
		return store;
	}

	public Properties getProperties(AGServer server, Office365ImapConfiguration configuration) {

		var properties = new Properties();
		properties.put("mail.store.protocol", "imap");
		properties.put("mail.imap.auth.mechanisms", "XOAUTH2");
		properties.put("mail.imap.connectiontimeout", configuration.connectionTimeout);
		properties.put("mail.imap.port", server.getPort());
		properties.put("mail.imap.ssl.enable", true);
		return properties;
	}

	private String getAccessToken(AGServer server, Office365ImapConfiguration configuration) {

		return Office365ImapConnectorData//
			.getAccessToken(server)
			.orElseGet(() -> renewAccessToken(server, configuration));
	}

	private String renewAccessToken(AGServer server, Office365ImapConfiguration configuration) {

		Log.finfo("renewing access token");
		var accessToken = new MicrosoftOnlineAuthorizer()
			.setAuthorityUrl(configuration.authorityUrl)
			.setTenantId(configuration.tenantId)
			.setClientId(configuration.clientId)
			.setClientSecret(configuration.clientSecret)
			.setGrantType("password")
			.setUsername(server.getUsername())
			.setPassword(server.getPassword())
			.addScope(configuration.accessScope)
			.authorize()
			.getAccessTokenOrThrow();
		Office365ImapConnectorData.save(server, accessToken);
		return accessToken;
	}
}
