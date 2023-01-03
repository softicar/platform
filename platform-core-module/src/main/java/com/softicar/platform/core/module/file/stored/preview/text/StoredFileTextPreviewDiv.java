package com.softicar.platform.core.module.file.stored.preview.text;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.download.StoredFileDownloadButton;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;

public class StoredFileTextPreviewDiv extends DomDiv {

	public StoredFileTextPreviewDiv(AGStoredFile file) {

		appendChild(new StoredFileDownloadButton(file));
		appendNewChild(DomElementTag.HR);
		appendChild(new StoredFileTextDisplay(file));
	}
}
