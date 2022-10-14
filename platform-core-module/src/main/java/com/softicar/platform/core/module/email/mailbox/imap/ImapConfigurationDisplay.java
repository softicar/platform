package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.label.DomLabelGrid;

class ImapConfigurationDisplay extends DomDiv {

	public ImapConfigurationDisplay(AGServer server) {

		var configuration = ImapConfiguration.fromJson(server.getConnectorConfiguration());
		var grid = new DomLabelGrid();
		grid.add(CoreI18n.PROTOCOL, configuration.protocol);
		grid.add(CoreI18n.TRUSTED_HOSTS, configuration.trustedHosts);
		grid.add(CoreI18n.CONNECTION_TIMEOUT, "" + configuration.connectionTimeout);
		appendChild(grid);
		appendChild(new DomActionBar(new ImapConnectorTestButton(server)));
	}
}
