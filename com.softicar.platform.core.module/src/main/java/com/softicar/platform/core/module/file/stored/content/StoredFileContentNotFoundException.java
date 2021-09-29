package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;

public class StoredFileContentNotFoundException extends SofticarUserException {

	public StoredFileContentNotFoundException(AGStoredFile storedFile) {

		super(CoreI18n.FAILED_TO_LOAD_THE_CONTENT_OF_THE_FILE_ARG1.toDisplay(storedFile.getFileName()));
	}
}
