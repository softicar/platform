package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.io.hash.HashingOutputStream;
import com.softicar.platform.common.string.formatting.MemoryFormatting;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileUtils;
import com.softicar.platform.core.module.file.stored.content.database.IStoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import com.softicar.platform.core.module.log.LogDb;
import java.io.OutputStream;

/**
 * This class manages the upload of the content of a stored file to the file
 * server.
 * <p>
 * This class requires the existence of a {@link AGStoredFile} entry,
 * representing the file to be uploaded. This class then manages the upload of
 * the associated file content to the file server.
 * <p>
 * Besides the upload of the file content, this class will also compute the hash
 * of the file content and save it to the database table
 * {@link AGStoredFileSha1}.
 *
 * @author Oliver Richers
 */
class StoredFileContentUploader {

	private final static long MINIMUM_FREE_SPACE = 1 * 1024 * 1024 * 1024; // 1 GB
	private static final String TEMPORARY_FILE_FOLDER = "/tmp";

	private final IStoredFileDatabase database;
	private final IStoredFileContentStore store;
	private final AGStoredFile storedFile;

	public StoredFileContentUploader(IStoredFileDatabase database, IStoredFileContentStore store, AGStoredFile storedFile) {

		this.database = database;
		this.store = store;
		this.storedFile = storedFile;
	}

	public OutputStream createOutputStream() {

		if (!store.isEnabled()) {
			return useChunks();
		}

		if (store.isReady()) {
			long freeDiskSpace = store.getFreeDiskSpace();
			if (freeDiskSpace >= MINIMUM_FREE_SPACE) {
				return useStore();
			} else {
				String message = "File store '%s' has not enough free space (%s free space required but only %s available). Falling back to database store."
					.formatted(store.getLocation(), MemoryFormatting.formatMemory(MINIMUM_FREE_SPACE, 1), MemoryFormatting.formatMemory(freeDiskSpace, 1));
				Log.ferror(message);
				LogDb.panic(message);
				return useChunks();
			}
		} else {
			String message = "File store '%s' is not available. Falling back to database store.".formatted(store.getLocation());
			Log.ferror(message);
			LogDb.panic(message);
			return useChunks();
		}
	}

	private OutputStream useChunks() {

		return database.createChunksOutputStream(storedFile);
	}

	private OutputStream useStore() {

		store.createDirectories(TEMPORARY_FILE_FOLDER);

		HashingOutputStream outputStream = new HashingOutputStream(() -> store.getFileOutputStream(getTemporaryFileName()), StoredFileUtils.FILE_HASH);
		outputStream.setOnCloseCallback(this::onClose);
		return outputStream;
	}

	private String getTemporaryFileName() {

		return TEMPORARY_FILE_FOLDER + "/" + storedFile.getId();
	}

	private void onClose(HashingOutputStream hashingStream) {

		byte[] hash = hashingStream.getFinalHash();
		long length = hashingStream.getTotalLength();

		database.saveFileHash(storedFile, hash, length);
		moveFile(hash);
	}

	private void moveFile(byte[] hash) {

		StoredFileContentName contentName = new StoredFileContentName(hash);

		createFolders(contentName.getFolderName());
		moveFileToFolder(contentName.getFullFilename());
	}

	private void createFolders(String folderName) {

		store.createDirectories(folderName);
	}

	private void moveFileToFolder(String targetName) {

		String sourceName = getTemporaryFileName();

		if (store.exists(targetName)) {

			verifyFileSizes(sourceName, targetName);
			store.deleteFile(sourceName);
		} else {
			store.moveFile(sourceName, targetName);
		}
	}

	private void verifyFileSizes(String sourceName, String targetName) {

		long sourceSize = store.getFileSize(sourceName);
		long targetSize = store.getFileSize(targetName);

		if (sourceSize != targetSize) {
			throw new SofticarDeveloperException(//
				"Files '%s' and '%s' have the same hash but different size.",
				sourceName,
				targetName);
		}
	}
}
