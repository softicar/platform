package com.softicar.platform.core.module.server.connector;

import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.dom.elements.DomDiv;

public class ServerConnectorConfigurationDisplay extends DomDiv {

	public ServerConnectorConfigurationDisplay(AGServer server) {

		server.getConnector().ifPresent(connector -> appendChild(connector.createConfigurationDisplay(server)));
	}
}
