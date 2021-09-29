package com.softicar.platform.core.module.file.stored.preview;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.download.StoredFileDownloadButton;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.EmfI18n;

public abstract class AbstractStoredFilePreviewPopup extends DomPopup {

	public AbstractStoredFilePreviewPopup(AGStoredFile file) {

		setCaption(EmfI18n.FILE);
		setSubCaption(IDisplayString.create(file.getFileName()));
		appendChild(new StoredFileDownloadButton(file));
		appendNewChild(DomElementTag.HR);
	}
}
