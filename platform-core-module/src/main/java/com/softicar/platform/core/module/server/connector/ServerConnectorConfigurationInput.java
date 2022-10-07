package com.softicar.platform.core.module.server.connector;

import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class ServerConnectorConfigurationInput extends AbstractDomValueInputDiv<String> implements IEmfInput<String> {

	private final AGServer server;
	private AGUuid connectorUuid;
	private Optional<IServerConnectorConfigurationInput> input;

	public ServerConnectorConfigurationInput(AGServer server) {

		this.server = server;

		refresh();
	}

	@Override
	public void refreshInputConstraints() {

		IEmfInput.super.refreshInputConstraints();

		if (server.getConnectorUuid() != connectorUuid) {
			refresh();
		}
	}

	@Override
	public void setValue(String configuration) {

		input.ifPresent(it -> it.setConfiguration(configuration));
	}

	@Override
	public Optional<String> getValue() {

		return input.map(it -> it.getConfiguration());
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		// nothing to do
	}

	private void refresh() {

		this.connectorUuid = server.getConnectorUuid();
		this.input = server.getConnector().map(connector -> connector.createConfigurationInput(server));

		removeChildren();
		input.ifPresent(input -> {
			input.setConfiguration(server.getConnectorConfiguration());
			appendChild(input);
		});
	}
}
