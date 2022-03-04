package com.softicar.platform.core.module.file.stored.attribute.upload;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.mime.CustomMimeType;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileBuilder;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFileInput;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

class StoredFileUploadForm extends DomForm implements IDomFileUploadHandler {

	private final StoredFileUploadTable table;
	private final DomDiv uploadDiv;

	public StoredFileUploadForm(StoredFileUploadTable table) {

		super(true);

		this.table = table;
		this.uploadDiv = appendChild(new UploadDiv());
		uploadDiv.appendChild(new FileInput(this));
	}

	public void attachFileUploadDiv() {

		if (uploadDiv.getParent() == null) {
			appendChild(uploadDiv);
		}
	}

	public void detachFileUploadDiv() {

		if (uploadDiv.getParent() != null) {
			uploadDiv.getParent().removeChild(uploadDiv);
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

	private class UploadDiv extends DomDiv {

		public UploadDiv() {

			addCssClass(CoreCssClasses.STORED_FILE_UPLOAD_DIV);
			appendChild(new DomImage(CoreImages.STORED_FILE_UPLOAD.getResource()));
			appendText(table.getNaming().getAddFileDisplayString());
		}
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
