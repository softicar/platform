package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.content.database.IStoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.database.StoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.content.store.StoredFileSmbContentStore;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Creates an output stream to write the content of an {@link AGStoredFile}.
 *
 * @author Oliver Richers
 */
public class StoredFileContentOutputStreamCreator {

	private final AGStoredFile storedFile;

	public StoredFileContentOutputStreamCreator(AGStoredFile storedFile) {

		this.storedFile = storedFile;
	}

	public OutputStream create() {

		IStoredFileDatabase database = new StoredFileDatabase();
		IStoredFileContentStore store = new StoredFileSmbContentStore();

		return new StoredFileContentUploader(database, store, storedFile).createOutputStream();
	}

	public void upload(InputStream inputStream) {

		try (OutputStream outputStream = create()) {
			StreamUtils.copyAndClose(inputStream, outputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
