package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.file.size.FileSize;
import com.softicar.platform.core.module.file.size.FileSizeFormatter;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.download.StoredFileDownloadButton;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssDisplay;
import com.softicar.platform.emf.EmfI18n;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

class StoredFileUploadTable extends DomDiv {

	private final IStoredFileUploadFileChangeHandler addHandler;
	private final IStoredFileUploadFileChangeHandler removeHandler;
	private final Map<AGStoredFile, FileRow> rows;
	private final INullaryVoidFunction callbackAfterAddOrRemove;

	public StoredFileUploadTable(IStoredFileUploadFileChangeHandler addHandler, IStoredFileUploadFileChangeHandler removeHandler,
			INullaryVoidFunction callbackAfterAddOrRemove) {

		this.addHandler = Objects.requireNonNull(addHandler);
		this.removeHandler = Objects.requireNonNull(removeHandler);
		this.callbackAfterAddOrRemove = Objects.requireNonNull(callbackAfterAddOrRemove);
		this.rows = new TreeMap<>();
		addCssClass(CoreCssClasses.STORED_FILE_UPLOAD_TABLE_DIV);
		refreshDisplayStyle();
	}

	public Collection<AGStoredFile> getFiles() {

		return rows.keySet();
	}

	public void setFiles(Collection<AGStoredFile> files) {

		var currentFiles = getFiles();
		if (!currentFiles.isEmpty()) {
			removeFiles(new ArrayList<>(currentFiles));
		}
		rows.clear();
		addFiles(files);
	}

	public void addFiles(Collection<AGStoredFile> files) {

		addHandler.handleChanged(getFiles(), files);
		for (AGStoredFile file: files) {
			FileRow fileRow = new FileRow(file);
			rows.put(file, fileRow);
			appendChild(fileRow);
		}
		refreshDisplayStyle();
		executeCallbackAfterAddOrRemove();
	}

	public void addFile(AGStoredFile file) {

		addFiles(Collections.singleton(file));
	}

	public void removeFile(AGStoredFile file) {

		removeFiles(List.of(file));
	}

	public void removeFiles(Collection<AGStoredFile> files) {

		removeHandler.handleChanged(getFiles(), files);
		for (var file: files) {
			removeChild(rows.remove(file));
		}
		refreshDisplayStyle();
		executeCallbackAfterAddOrRemove();
	}

	public void setDisabled(boolean disabled) {

		rows.values().forEach(row -> row.setDisabled(disabled));
	}

	private void refreshDisplayStyle() {

		if (rows.isEmpty()) {
			setStyle(CssDisplay.NONE);
		} else {
			unsetStyle(CssStyle.DISPLAY);
		}
	}

	private void executeCallbackAfterAddOrRemove() {

		callbackAfterAddOrRemove.apply();
	}

	private class FileRow extends DomDiv {

		private final DomActionBar bar;
		private final DomButton removeButton;

		public FileRow(AGStoredFile file) {

			this.removeButton = new DomButton()//
				.setIcon(DomElementsImages.ENTRY_REMOVE.getResource())
				.setTitle(EmfI18n.REMOVE_FILE)
				.setConfirmationMessage(EmfI18n.ARE_YOU_SURE_QUESTION)
				.setClickCallback(() -> removeFile(file))
				.addMarker(CoreTestMarker.STORED_FILE_REMOVE_FILE_BUTTON);

			this.bar = appendChild(new DomActionBar());
			this.bar.appendChild(removeButton);
			this.bar.appendChild(new StoredFileDownloadButton(file));
			this.bar.appendText(new FileSizeFormatter().formatToHumanReadableBase(new FileSize(file.getFileSize())));
		}

		public void setDisabled(boolean disabled) {

			if (disabled) {
				removeButton.disappend();
			} else {
				if (removeButton.getParent() == null) {
					bar.prependChild(removeButton);
				}
			}
		}
	}
}
