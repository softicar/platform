package com.softicar.platform.core.module.file.stored.attribute.upload;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.core.module.CoreCssClasses;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

class StoredFileUploadTable extends DomDiv {

	private final IStoredFileUploadNaming naming;
	private Optional<IStoredFileUploadSaveHook> saveHook;
	private final Map<AGStoredFile, FileRow> rows;
	private IRefreshable refreshable;

	public StoredFileUploadTable(IStoredFileUploadNaming naming) {

		this.naming = naming;
		this.saveHook = Optional.empty();
		this.rows = new TreeMap<>();
		addCssClass(CoreCssClasses.STORED_FILE_UPLOAD_TABLE_DIV);
		refreshDisplayStyle();
	}

	public StoredFileUploadTable setSaveHook(IStoredFileUploadSaveHook saveHook) {

		this.saveHook = Optional.of(saveHook);
		return this;
	}

	public IStoredFileUploadNaming getNaming() {

		return naming;
	}

	public Collection<AGStoredFile> getFiles() {

		return rows.keySet();
	}

	public void setFiles(Collection<AGStoredFile> files) {

		rows//
			.keySet()
			.stream()
			.map(rows::get)
			.forEach(this::removeChild);
		rows.clear();
		addFiles(files);
	}

	public void addFiles(Collection<AGStoredFile> files) {

		if (saveHook.isPresent()) {
			saveHook.get().handleFileAddition(getFiles(), files);
		}
		for (AGStoredFile file: files) {
			FileRow fileRow = new FileRow(file);
			rows.put(file, fileRow);
			appendChild(fileRow);
		}
		refreshDisplayStyle();
		executeRefresh();
	}

	public void addFile(AGStoredFile file) {

		addFiles(Collections.singleton(file));
	}

	public void removeFile(AGStoredFile file) {

		if (saveHook.isPresent()) {
			saveHook.get().handleFileRemoval(getFiles(), file);
		}
		FileRow fileRow = rows.remove(file);
		removeChild(fileRow);
		refreshDisplayStyle();
		executeRefresh();
	}

	private void executeRefresh() {

		if (refreshable != null) {
			refreshable.refresh();
		}
	}

	private void refreshDisplayStyle() {

		if (rows.isEmpty()) {
			setStyle(CssDisplay.NONE);
		} else {
			unsetStyle(CssStyle.DISPLAY);
		}
	}

	private class FileRow extends DomDiv {

		public FileRow(AGStoredFile file) {

			var bar = appendChild(new DomActionBar());
			bar
				.appendChild(
					new DomButton()//
						.setIcon(DomElementsImages.ENTRY_REMOVE.getResource())
						.setTitle(naming.getRemoveFileDisplayString())
						.setConfirmationMessage(EmfI18n.ARE_YOU_SURE_QUESTION)
						.setClickCallback(() -> removeFile(file)));
			bar.appendChild(new StoredFileDownloadButton(file));
			bar.appendText(new FileSizeFormatter().formatToHumanReadableBase(new FileSize(file.getFileSize())));
		}
	}

	public void setRefreshable(IRefreshable refreshable) {

		Objects.requireNonNull(refreshable);
		this.refreshable = refreshable;

	}
}
