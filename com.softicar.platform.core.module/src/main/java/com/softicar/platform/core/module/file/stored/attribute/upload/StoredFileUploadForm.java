package com.softicar.platform.core.module.file.stored.attribute.upload;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.mime.CustomMimeType;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileBuilder;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.DomFileInput;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

class StoredFileUploadForm extends DomForm implements IDomFileUploadHandler {

	private final StoredFileUploadTable table;
	private final FileInput fileInput;
	private final DomButton button;

	public StoredFileUploadForm(StoredFileUploadTable table) {

		super(true);

		this.table = table;

		DomActionBar actionBar = appendChild(new DomActionBar());
		button = actionBar//
			.appendChild(new DomButton())
			.setIcon(CoreImages.STORED_FILE_UPLOAD.getResource())
			.setLabel(table.getNaming().getAddFileDisplayString());
		this.fileInput = button.appendChild(new FileInput(this));

		getDomEngine().setClickTargetForEventDelegation(button, DomEventType.ENTER, fileInput);
		getDomEngine().setClickTargetForEventDelegation(button, DomEventType.SPACE, fileInput);
	}

	public void attachButton() {

		if (button.getParent() == null) {
			appendChild(button);
		}
	}

	public void detachButton() {

		if (button.getParent() != null) {
			button.getParent().removeChild(button);
		}
	}

	@Override
	public void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

		Collection<AGStoredFile> files = saveFiles(fileUploads);
		table.addFiles(files);
	}

	private Collection<AGStoredFile> saveFiles(Iterable<IDomFileUpload> fileUploads) {

		Collection<AGStoredFile> files = new ArrayList<>();
		try (DbTransaction transaction = new DbTransaction()) {
			for (IDomFileUpload upload: fileUploads) {
				AGStoredFile file = new StoredFileBuilder()
					.setContentType(new CustomMimeType(upload.getContentType()))
					.setCreatedBy(CurrentUser.get())
					.setFilename(upload.getFilename())
					.setRemoveAt(DayTime.now().plusDays(1))
					.build();

				try (InputStream inputStream = upload.getStream()) {
					file.uploadFileContent(inputStream);
				} catch (IOException exception) {
					throw new RuntimeException(exception);
				}

				files.add(file);
			}
			transaction.commit();
		}
		return files;
	}

	private class FileInput extends DomFileInput {

		public FileInput(StoredFileUploadForm form) {

			addCssClass(CoreCssClasses.STORED_FILE_UPLOAD_INPUT);
			setTabIndex(-1);
			setMultiple(true);

			form.submitOnChange(this);
		}
	}
}
