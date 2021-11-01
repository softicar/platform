package com.softicar.platform.core.module.file.stored.attribute.upload;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.Collections;

public class StoredSingleFileUploadDiv extends DomDiv implements IRefreshable {

	private final StoredFileUploadTable table;
	private final StoredFileUploadForm uploadForm;

	public StoredSingleFileUploadDiv(IStoredFileUploadSaveHook saveHook) {

		this(new StoredFileUploadDefaultNaming(), saveHook);
	}

	public StoredSingleFileUploadDiv(IStoredFileUploadNaming naming, IStoredFileUploadSaveHook saveHook) {

		this.table = new StoredFileUploadTable(naming);
		this.table.setSaveHook(saveHook);
		this.table.setRefreshable(this);
		this.uploadForm = appendChild(new StoredFileUploadForm(table));
		appendChild(table);
	}

	public StoredSingleFileUploadDiv setFile(AGStoredFile storedFile) {

		table.setFiles(storedFile == null? Collections.emptyList() : Collections.singleton(storedFile));
		refresh();
		return this;
	}

	@Override
	public void refresh() {

		if (table.getFiles().isEmpty()) {
			uploadForm.unsetStyle(CssStyle.DISPLAY);
		} else {
			uploadForm.setStyle(CssDisplay.NONE);
		}
	}
}
