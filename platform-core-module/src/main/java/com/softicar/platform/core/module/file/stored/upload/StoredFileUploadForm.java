package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.mime.CustomMimeType;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileBuilder;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.upload.DomUploadForm;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

class StoredFileUploadForm extends DomUploadForm {

	private final StoredFileUploadTable table;
	private final boolean multiple;

	public StoredFileUploadForm(StoredFileUploadTable table, boolean multiple) {

		super(multiple);

		this.table = table;
		this.multiple = multiple;

		setUploadHandler(this::uploadFiles);
	}

	private void uploadFiles(Iterable<IDomFileUpload> fileUploads) {

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
}
