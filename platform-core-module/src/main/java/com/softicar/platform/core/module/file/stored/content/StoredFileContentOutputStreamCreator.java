package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.content.database.IStoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.database.StoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.content.store.StoredFileContentStores;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Creates an output stream to write the content of an {@link AGStoredFile}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class StoredFileContentOutputStreamCreator {

	private final AGStoredFile storedFile;
	private final IStoredFileDatabase database;
	private IStoredFileContentStore store;

	public StoredFileContentOutputStreamCreator(AGStoredFile storedFile) {

		this.storedFile = Objects.requireNonNull(storedFile);
		this.database = new StoredFileDatabase();
		this.store = StoredFileContentStores.getPrimaryContentStore().orElse(null);
	}

	public StoredFileContentOutputStreamCreator setStore(IStoredFileContentStore store) {

		this.store = store;
		return this;
	}

	public OutputStream create() {

		return new StoredFileContentUploader(database, storedFile, store).createOutputStream();
	}

	public void upload(InputStream inputStream) {

		try (OutputStream outputStream = create()) {
			StreamUtils.copyAndClose(inputStream, outputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
