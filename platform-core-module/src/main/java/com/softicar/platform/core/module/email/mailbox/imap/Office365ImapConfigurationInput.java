package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.server.connector.IServerConnectorConfigurationInput;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.attribute.field.string.EmfPasswordInput;

class Office365ImapConfigurationInput extends DomDiv implements IServerConnectorConfigurationInput {

	private final DomTextInput tenantId;
	private final DomTextInput clientId;
	private final EmfPasswordInput clientSecret;
	private final DomIntegerInput connectionTimeout;

	public Office365ImapConfigurationInput() {

		this.tenantId = new DomTextInput();
		this.clientId = new DomTextInput();
		this.clientSecret = new EmfPasswordInput();
		this.connectionTimeout = new DomIntegerInput();

		var grid = new DomLabelGrid();
		grid.add(CoreI18n.TENANT_ID, tenantId);
		grid.add(CoreI18n.CLIENT_ID, clientId);
		grid.add(CoreI18n.CLIENT_SECRET, clientSecret);
		grid.add(CoreI18n.CONNECTION_TIMEOUT, connectionTimeout);
		appendChild(grid);
	}

	@Override
	public void setConfiguration(String configurationString) {

		var configuration = Office365ImapConfiguration.fromJson(configurationString);

		tenantId.setValue(configuration.tenantId);
		clientId.setValue(configuration.clientId);
		clientSecret.setValue(configuration.clientSecret);
		connectionTimeout.setValue(configuration.connectionTimeout);
	}

	@Override
	public String getConfiguration() {

		var configuration = new Office365ImapConfiguration();
		configuration.tenantId = tenantId.getValueText();
		configuration.clientId = clientId.getValueText();
		configuration.clientSecret = clientSecret.getValue().orElse("");
		configuration.connectionTimeout = connectionTimeout.getValueOrThrow();
		return configuration.toJson();
	}
}
