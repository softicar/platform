package com.softicar.platform.core.module.file.stored.preview.text;

import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.preview.AbstractStoredFilePreviewPopup;
import com.softicar.platform.dom.elements.DomDiv;

public class StoredFileTextPreviewPopup extends AbstractStoredFilePreviewPopup {

	public StoredFileTextPreviewPopup(AGStoredFile file) {

		super(file);
		appendChild(new TextDisplayDiv(file));
	}

	private class TextDisplayDiv extends DomDiv {

		public TextDisplayDiv(AGStoredFile file) {

			setCssClass(CoreCssClasses.STORED_FILE_TEXT_DISPLAY);
			appendChild(new String(file.getFileContentBytes(), Charsets.UTF8));
		}
	}
}
