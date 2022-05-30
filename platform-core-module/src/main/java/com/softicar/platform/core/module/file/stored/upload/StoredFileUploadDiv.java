package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.Collection;

public class StoredFileUploadDiv extends DomDiv {

	private final StoredFileUploadTable table;
	private final StoredFileUploadForm form;
	private final boolean multiple;
	private boolean disabled;

	public StoredFileUploadDiv(IStoredFileUploadFileChangeHandler addHandler, IStoredFileUploadFileChangeHandler removeHandler, boolean multiple) {

		this.table = new StoredFileUploadTable(addHandler, removeHandler, this::disappendOrPrependForm);
		this.form = new StoredFileUploadForm(table, multiple);
		this.multiple = multiple;
		this.disabled = false;

		appendChild(form);
		appendChild(table);
	}

	public StoredFileUploadDiv setFiles(Collection<AGStoredFile> files) {

		table.setFiles(files);
		return this;
	}

	public StoredFileUploadDiv setDisabled(boolean disabled) {

		this.disabled = disabled;
		table.setDisabled(disabled);
		disappendOrPrependForm();
		return this;
	}

	public boolean isDisabled() {

		return disabled;
	}

	private boolean isNoMoreFilesAllowed() {

		return isSingle() && hasFiles();
	}

	private boolean isSingle() {

		return !multiple;
	}

	private boolean hasFiles() {

		return !table.getFiles().isEmpty();
	}

	private void disappendOrPrependForm() {

		if (isNoMoreFilesAllowed() || disabled) {
			form.disappend();
		} else if (form.getParent() == null) {
			prependChild(form);
		}
	}
}
