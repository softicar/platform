package com.softicar.platform.core.module.file.stored.access.log;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.management.EmfManagementDiv;

public class StoredFileAccessLogPopup extends DomPopup {

	public StoredFileAccessLogPopup(AGStoredFile file) {

		setCaption(CoreI18n.STORED_FILE_ACCESS_LOGS);
		setSubCaption(IDisplayString.create(file.getFileName()));

		appendChild(new EmfManagementDiv<>(AGStoredFileAccessLog.TABLE, file));
		appendCloseButton();
	}
}
