package com.softicar.platform.core.module.file.stored.server;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.validation.AbstractEmfValidator;

public class StoredFileServerValidator extends AbstractEmfValidator<AGStoredFileServer> {

	@Override
	protected void validate() {

		if (tableRow.isPrimary() && !tableRow.isActive()) {
			addError(AGStoredFileServer.ACTIVE, CoreI18n.CANNOT_DEACTIVATE_PRIMARY_FILE_SERVER);
		}
	}
}
