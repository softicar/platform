package com.softicar.platform.core.module.file.stored.preview;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.EmfI18n;

public abstract class AbstractStoredFilePreviewPopup extends DomPopup {

	protected AGStoredFile file;

	public AbstractStoredFilePreviewPopup(AGStoredFile file) {

		this.file = file;

		setCaption(EmfI18n.FILE);
		setSubCaption(IDisplayString.create(file.getFileName()));
	}
}
