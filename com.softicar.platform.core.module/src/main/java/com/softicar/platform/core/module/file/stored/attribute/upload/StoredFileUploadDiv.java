package com.softicar.platform.core.module.file.stored.attribute.upload;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.Collection;

public class StoredFileUploadDiv extends DomDiv {

	private final StoredFileUploadTable table;
	private final StoredFileUploadForm uploadForm;

	public StoredFileUploadDiv() {

		this(new StoredFileUploadDefaultNaming());
	}

	public StoredFileUploadDiv(IStoredFileUploadNaming naming) {

		this.table = new StoredFileUploadTable(naming);
		this.uploadForm = new StoredFileUploadForm(table);
		appendChild(uploadForm);
		appendChild(table);
	}

	public StoredFileUploadDiv setSaveHook(IStoredFileUploadSaveHook saveHook) {

		this.table.setSaveHook(saveHook);
		return this;
	}

	public Collection<AGStoredFile> getFiles() {

		return table.getFiles();
	}

	public StoredFileUploadDiv setFiles(Collection<AGStoredFile> files) {

		table.setFiles(files);
		return this;
	}
}
