package com.softicar.platform.core.module.server.connector;

import com.softicar.platform.dom.node.IDomNode;

/**
 * An {@link IDomNode} to configure an {@link IServerConnector}.
 *
 * @author Oliver Richers
 */
public interface IServerConnectorConfigurationInput extends IDomNode {

	/**
	 * Applies the given configuration {@link String} to the UI.
	 *
	 * @param configurationString
	 *            the configuration {@link String} (never <i>null</i>)
	 */
	void setConfiguration(String configurationString);

	/**
	 * Returns the configuration {@link String} from the UI.
	 *
	 * @return the configuration {@link String} (never <i>null</i>)
	 */
	String getConfiguration();
}
