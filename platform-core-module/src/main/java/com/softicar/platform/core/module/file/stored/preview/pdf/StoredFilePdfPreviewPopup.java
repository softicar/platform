package com.softicar.platform.core.module.file.stored.preview.pdf;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.preview.AbstractStoredFilePreviewPopup;

public class StoredFilePdfPreviewPopup extends AbstractStoredFilePreviewPopup {

	public StoredFilePdfPreviewPopup(AGStoredFile file, StoredFilePdfDisplayConfiguration configuration) {

		super(file);

		appendChild(new StoredFilePdfDisplay(configuration));
	}

	public StoredFilePdfPreviewPopup(AGStoredFile file) {

		this(file, new StoredFilePdfDisplayConfiguration(file::getFileContentInputStream, file.getFileName()));
	}
}
