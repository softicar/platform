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

	public boolean isPrimary() {

		return getPrimary()//
			.map(it -> it.equals(this))
			.orElse(false);
	}

	public static List<AGStoredFileServer> getAllActiveWithPrimaryFirst() {

		List<AGStoredFileServer> servers = new ArrayList<>();
		Optional<AGStoredFileServer> primaryServer = getPrimary();
		primaryServer.ifPresent(servers::add);
		AGStoredFileServer.TABLE//
			.createSelect()
			.where(AGStoredFileServer.ACTIVE)
			.whereIf(primaryServer.isPresent(), () -> AGStoredFileServer.ID.isNotEqual(primaryServer.get()))
			.orderBy(AGStoredFileServer.ID)
			.forEach(servers::add);
		return servers;
	}

	public static Optional<AGStoredFileServer> getPrimary() {

		return Optional//
			.ofNullable(AGCoreModuleInstance.getInstance())
			.map(AGCoreModuleInstance::getPrimaryFileServer)
			.map(it -> it.isActive()? it : null);
	}
}
