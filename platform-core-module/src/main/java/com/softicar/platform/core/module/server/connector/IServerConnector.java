package com.softicar.platform.core.module.server.connector;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.node.DomNode;

/**
 * A {@link ISourceCodeReferencePoint} for connectors to {@link AGServer}
 * instances.
 *
 * @author Oliver Richers
 */
public interface IServerConnector extends ISourceCodeReferencePoint {

	/**
	 * Creates a new {@link DomNode} to display the configuration of the given
	 * {@link AGServer}.
	 *
	 * @param server
	 *            the {@link AGServer} object (never <i>null</i>)
	 * @return the new {@link DomElement} instance
	 */
	DomElement createConfigurationDisplay(AGServer server);

	/**
	 * Creates a new {@link IServerConnectorConfigurationInput} instance for the
	 * given {@link AGServer}.
	 *
	 * @param server
	 *            the {@link AGServer} object (never <i>null</i>)
	 * @return the new {@link IServerConnectorConfigurationInput} instance
	 */
	IServerConnectorConfigurationInput createConfigurationInput(AGServer server);
}
