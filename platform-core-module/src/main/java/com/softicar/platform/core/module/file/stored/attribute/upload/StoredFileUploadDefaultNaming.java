package com.softicar.platform.core.module.file.stored.attribute.upload;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.EmfI18n;

class StoredFileUploadDefaultNaming implements IStoredFileUploadNaming {

	@Override
	public IDisplayString getAddFileDisplayString() {

		return EmfI18n.CHOOSE_OR_DROP_FILE;
	}

	@Override
	public IDisplayString getRemoveFileDisplayString() {

		return EmfI18n.REMOVE_FILE;
	}
}
