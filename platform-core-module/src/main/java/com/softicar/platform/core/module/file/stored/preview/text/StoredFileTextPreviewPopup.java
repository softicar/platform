package com.softicar.platform.core.module.file.stored.preview.text;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.preview.AbstractStoredFilePreviewPopup;

public class StoredFileTextPreviewPopup extends AbstractStoredFilePreviewPopup {

	public StoredFileTextPreviewPopup(AGStoredFile file) {

		super(file);

		appendChild(new StoredFileTextPreviewDiv(file));
	}
}
