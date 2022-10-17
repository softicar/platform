package com.softicar.platform.core.module.server.connector;

import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoint;
import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoints;
import java.util.Collection;

public class ServerConnectors {

	public static Collection<AGUuidBasedSourceCodeReferencePoint> getAllServerConnectorsAsIndirectEntities() {

		return AGUuidBasedSourceCodeReferencePoints.getAll(IServerConnector.class);
	}
}
