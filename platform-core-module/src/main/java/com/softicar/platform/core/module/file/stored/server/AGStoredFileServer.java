package com.softicar.platform.core.module.file.stored.server;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AGStoredFileServer extends AGStoredFileServerGenerated implements IEmfObject<AGStoredFileServer> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getUrl());
	}

	public static List<AGStoredFileServer> getAllActiveWithPrimaryFirst() {

		List<AGStoredFileServer> servers = new ArrayList<>();
		Optional<AGStoredFileServer> primaryServer = getInstance();
		getInstance().ifPresent(servers::add);
		AGStoredFileServer.TABLE//
			.createSelect()
			.where(AGStoredFileServer.ACTIVE)
			.whereIf(primaryServer.isPresent(), () -> AGStoredFileServer.ID.isNotEqual(primaryServer.get()))
			.orderBy(AGStoredFileServer.ID)
			.forEach(servers::add);
		return servers;
	}

	public static Optional<AGStoredFileServer> getInstance() {

		return Optional//
			.ofNullable(AGCoreModuleInstance.getInstance())
			.map(AGCoreModuleInstance::getPrimaryFileServer);
	}
}
