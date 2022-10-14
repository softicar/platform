package com.softicar.platform.core.module.server.connector;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.dom.node.IDomNode;

/**
 * An {@link ISourceCodeReferencePoint} for connectors to {@link AGServer}
 * instances.
 *
 * @author Oliver Richers
 */
public interface IServerConnector extends ISourceCodeReferencePoint {

	/**
	 * Creates a new {@link IDomNode} to display the configuration of the given
	 * {@link AGServer}.
	 *
	 * @param server
	 *            the {@link AGServer} object (never <i>null</i>)
	 * @return the new {@link IDomNode} instance (never <i>null</i>)
	 */
	IDomNode createConfigurationDisplay(AGServer server);

	/**
	 * Creates a new {@link IServerConnectorConfigurationInput} instance for the
	 * given {@link AGServer}.
	 *
	 * @param server
	 *            the {@link AGServer} object (never <i>null</i>)
	 * @return the new {@link IServerConnectorConfigurationInput} instance
	 *         (never <i>null</i>)
	 */
	IServerConnectorConfigurationInput createConfigurationInput(AGServer server);
}
