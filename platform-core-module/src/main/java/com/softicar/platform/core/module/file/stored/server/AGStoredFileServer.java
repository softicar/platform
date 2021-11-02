package com.softicar.platform.core.module.file.stored.server;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Optional;

public class AGStoredFileServer extends AGStoredFileServerGenerated implements IEmfObject<AGStoredFileServer> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getUrl());
	}

	public static Optional<AGStoredFileServer> getInstance() {

		return Optional//
			.ofNullable(AGCoreModuleInstance.getInstance())
			.map(AGCoreModuleInstance::getPrimaryFileServer);
	}
}
