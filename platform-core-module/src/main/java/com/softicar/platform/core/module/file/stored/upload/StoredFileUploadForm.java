package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.mime.CustomMimeType;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileBuilder;
import com.softicar.platform.core.module.file.stored.StoredFileMarker;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFileInput;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import com.softicar.platform.emf.EmfI18n;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

class StoredFileUploadForm extends DomForm implements IDomFileUploadHandler {

	private final StoredFileUploadTable table;
	private final boolean multiple;

	public StoredFileUploadForm(StoredFileUploadTable table, boolean multiple) {

		super(true);
		this.table = table;
		this.multiple = multiple;

		appendChild(new AddFileElement(multiple));
		addMarker(StoredFileMarker.UPLOAD_FORM);
	}

	@Override
	public void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

		var files = saveFiles(fileUploads);
		table.addFiles(files);
	}

	private Collection<AGStoredFile> saveFiles(Iterable<IDomFileUpload> fileUploads) {

		var uploadedFiles = new ArrayList<AGStoredFile>();
		try (var transaction = new DbTransaction()) {
			for (var upload: fileUploads) {

				boolean hasFiles = !table.getFiles().isEmpty() || !uploadedFiles.isEmpty();
				if (hasFiles && !multiple) {
					throw new SofticarUserException(CoreI18n.ONLY_A_SINGLE_FILE_MAY_BE_UPLOADED);
				}

				var file = new StoredFileBuilder()
					.setContentType(new CustomMimeType(upload.getContentType()))
					.setCreatedBy(CurrentUser.get())
					.setFilename(upload.getFilename())
					.setRemoveAt(DayTime.now().plusDays(1))
					.build();

				try (var inputStream = upload.getStream()) {
					file.uploadFileContent(inputStream);
				} catch (IOException exception) {
					throw new RuntimeException(exception);
				}

				uploadedFiles.add(file);
			}
			transaction.commit();
		}
		return uploadedFiles;
	}

	private class AddFileElement extends DomDiv {

		public AddFileElement(boolean multiple) {

			addCssClass(CoreCssClasses.STORED_FILE_UPLOAD_DIV);
			appendChild(new DomImage(CoreImages.STORED_FILE_UPLOAD.getResource()));
			appendText(EmfI18n.CHOOSE_OR_DROP_FILE);
			addMarker(StoredFileMarker.ADD_FILE_ELEMENT);
			appendChild(new FileInput(multiple));
		}
	}

	private class FileInput extends DomFileInput {

		public FileInput(boolean multiple) {

			addCssClass(CoreCssClasses.STORED_FILE_UPLOAD_INPUT);
			setTabIndex(-1);
			setMultiple(multiple);
			triggerUploadOnChange(this);
		}
	}
}
