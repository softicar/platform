package com.softicar.platform.core.module.file.stored.preview.text;

import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.dom.elements.DomDiv;

public class StoredFileTextDisplay extends DomDiv {

	public StoredFileTextDisplay(AGStoredFile file) {

		setCssClass(CoreCssClasses.STORED_FILE_TEXT_DISPLAY);
		appendChild(new String(file.getFileContentBytes(), Charsets.UTF8));
	}
}
