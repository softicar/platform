package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StoredFileUploader {

	private final InputStream inputStream;
	private final String filename;
	private final String contentType;
	private AGStoredFile storedFile;

	public StoredFileUploader(InputStream inputStream, String filename, MimeType contentType) {

		this(inputStream, filename, contentType.getIdentifier());
	}

	public StoredFileUploader(InputStream inputStream, String filename, String contentType) {

		this.inputStream = inputStream;
		this.filename = filename;
		this.contentType = contentType;
	}

	public AGStoredFile upload() {

		try (DbTransaction transaction = new DbTransaction()) {
			openStoredFile();
			uploadFileContent();
			transaction.commit();
		} catch (Exception exception) {
			throw new SofticarException(exception);
		}

		return storedFile;
	}

	private void openStoredFile() {

		storedFile = new AGStoredFile();
		storedFile.setCreatedBy(CurrentUser.get());
		storedFile.setCreatedAt(DayTime.now());
		storedFile.setFileName(filename);
		storedFile.setContentType(contentType);
		storedFile.setRemoveAt(null);
		storedFile.saveLogged();
	}

	private void uploadFileContent() throws IOException {

		try (OutputStream outputStream = storedFile.getFileContentOutputStream()) {
			StreamUtils.copyAndClose(inputStream, outputStream);
		}
	}
}
