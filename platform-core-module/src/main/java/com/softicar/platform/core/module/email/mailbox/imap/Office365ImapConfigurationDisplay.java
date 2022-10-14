package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.emf.attribute.field.string.EmfPasswordDisplay;

class Office365ImapConfigurationDisplay extends DomDiv {

	public Office365ImapConfigurationDisplay(AGServer server) {

		var configuration = Office365ImapConfiguration.fromJson(server.getConnectorConfiguration());
		var grid = new DomLabelGrid();
		grid.add(CoreI18n.AUTHORITY_URL, configuration.authorityUrl);
		grid.add(CoreI18n.ACCESS_SCOPE, configuration.accessScope);
		grid.add(CoreI18n.TENANT_ID, configuration.tenantId);
		grid.add(CoreI18n.CLIENT_ID, configuration.clientId);
		grid.add(CoreI18n.CLIENT_SECRET, new EmfPasswordDisplay(configuration.clientSecret));
		grid.add(CoreI18n.CONNECTION_TIMEOUT, "" + configuration.connectionTimeout);
		appendChild(grid);
		appendChild(new DomActionBar(new ImapConnectorTestButton(server)));
	}
}
