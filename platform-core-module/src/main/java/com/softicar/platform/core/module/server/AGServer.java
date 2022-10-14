package com.softicar.platform.core.module.server;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.email.transport.IEmailTransportServer;
import com.softicar.platform.core.module.server.connector.IServerConnector;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Optional;

public class AGServer extends AGServerGenerated implements IEmfObject<AGServer>, IEmailTransportServer {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	public Optional<IServerConnector> getConnector() {

		return Optional//
			.ofNullable(getConnectorUuid())
			.flatMap(connectorUuid -> SourceCodeReferencePoints.getReferencePoint(connectorUuid.getUuid(), IServerConnector.class));
	}
}
