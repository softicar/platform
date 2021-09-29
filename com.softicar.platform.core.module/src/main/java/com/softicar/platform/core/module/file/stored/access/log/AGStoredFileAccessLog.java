package com.softicar.platform.core.module.file.stored.access.log;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGStoredFileAccessLog extends AGStoredFileAccessLogGenerated implements IEmfObject<AGStoredFileAccessLog> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString
			.format(//
				"%s - %s - %s",
				getFile().toDisplayWithoutId(),
				getAccessedBy().toDisplayWithoutId(),
				getAccessedAt());
	}
}
