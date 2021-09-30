package com.softicar.platform.core.module.file.stored.attribute;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.download.StoredFileViewOrDownloadButton;
import com.softicar.platform.dom.elements.bar.DomActionBar;

public class StoredFileDisplay extends DomActionBar {

	public StoredFileDisplay(AGStoredFile storedFile) {

		appendChild(new StoredFileViewOrDownloadButton(storedFile));
	}
}
