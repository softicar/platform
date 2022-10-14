package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.server.connector.IServerConnectorConfigurationInput;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.input.DomTextInput;

class ImapConfigurationInput extends DomDiv implements IServerConnectorConfigurationInput {

	private final DomTextInput protocol;
	private final DomTextInput trustedHosts;
	private final DomIntegerInput connectionTimeout;

	public ImapConfigurationInput() {

		this.protocol = new DomTextInput();
		this.trustedHosts = new DomTextInput();
		this.connectionTimeout = new DomIntegerInput();

		var grid = new DomLabelGrid();
		grid.add(CoreI18n.PROTOCOL, protocol);
		grid.add(CoreI18n.TRUSTED_HOSTS, trustedHosts);
		grid.add(CoreI18n.CONNECTION_TIMEOUT, connectionTimeout);
		appendChild(grid);
	}

	@Override
	public void setConfiguration(String configurationString) {

		var configuration = ImapConfiguration.fromJson(configurationString);

		protocol.setValue(configuration.protocol);
		trustedHosts.setValue(configuration.trustedHosts);
		connectionTimeout.setValue(configuration.connectionTimeout);
	}

	@Override
	public String getConfiguration() {

		var configuration = new ImapConfiguration();
		configuration.protocol = protocol.getValueText();
		configuration.trustedHosts = trustedHosts.getValueText();
		configuration.connectionTimeout = connectionTimeout.getValueOrThrow();
		return configuration.toJson();
	}
}
