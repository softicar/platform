package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.object.IEmfObject;

public class AGStoredFileLog extends AGStoredFileLogGenerated implements IEmfObject<AGStoredFileLog> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return CoreI18n.STORED_FILE_LOG_OF_ARG1.toDisplay(getFile().toDisplay());
	}
}
