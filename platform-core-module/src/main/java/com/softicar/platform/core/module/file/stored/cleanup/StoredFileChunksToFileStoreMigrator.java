package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.chunk.AGStoredFileChunk;
import com.softicar.platform.core.module.file.stored.content.StoredFileContentName;
import com.softicar.platform.core.module.file.stored.content.StoredFileContentOutputStreamCreator;
import com.softicar.platform.core.module.file.stored.content.database.IStoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.database.StoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.content.store.StoredFileSmbContentStore;
import com.softicar.platform.core.module.file.stored.hash.IStoredFileHash;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

class StoredFileChunksToFileStoreMigrator {

	private final IStoredFileDatabase database;
	private final IStoredFileContentStore store;
	private final AGStoredFile storedFile;

	public StoredFileChunksToFileStoreMigrator(IStoredFileDatabase database, IStoredFileContentStore store, AGStoredFile storedFile) {

		this.database = database;
		this.store = store;
		this.storedFile = storedFile;
	}

	public static void migrateAll() {

		migrateAll(new StoredFileDatabase(), new StoredFileSmbContentStore());
	}

	public static void migrateAll(IStoredFileDatabase database, IStoredFileContentStore store) {

		try {
			for (AGStoredFile file: getFilesWithChunks()) {
				new StoredFileChunksToFileStoreMigrator(database, store, file).migrate();
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private static List<AGStoredFile> getFilesWithChunks() {

		return AGStoredFile.TABLE//
			.createSelect()
			.groupBy(AGStoredFile.ID)
			.joinReverse(AGStoredFileChunk.FILE)
			.list();
	}

	private void migrate() throws IOException {

		IStoredFileHash hash = database.getFileHash(storedFile);
		if (database.getChunkCount(storedFile) > 0) {

			if (hash == null) {
				Log.finfo("file %s has chunk content but no hash", storedFile.getId());
				try (InputStream inputStream = database.createChunksInputStream(storedFile)) {
					new StoredFileContentOutputStreamCreator(storedFile)//
						.setStore(store)
						.upload(inputStream);
				}
				removeChunks();
			} else {
				Log.finfo("file %s has chunk content but also hash", storedFile.getId());
				removeChunks();
			}
		} else {

			Log.finfo("file %s has only hash", storedFile.getId());
		}
	}

	private void removeChunks() throws IOException {

		byte[] hash = database.getFileHash(storedFile).getHash();
		byte[] chunkHash = computeChunkContentHash();
		byte[] storeHash = computeStoreContentHash();

		if (Arrays.equals(hash, chunkHash) && Arrays.equals(hash, storeHash)) {
			AGStoredFileChunk.TABLE.createDelete().where(AGStoredFileChunk.FILE.equal(storedFile)).execute();
			Log.finfo("savely removed chunk content of file %s", storedFile.getId());
		} else {
			throw new SofticarDeveloperException("Hash mismatch with file %s.", storedFile.getId());
		}
	}

	private byte[] computeChunkContentHash() throws IOException {

		MessageDigest digest = Hash.SHA1.createDigest();
		try (InputStream inputStream = database.createChunksInputStream(storedFile)) {
			StreamUtils.copy(inputStream, digest);
		}
		return digest.digest();
	}

	private byte[] computeStoreContentHash() throws IOException {

		MessageDigest digest = Hash.SHA1.createDigest();
		try (InputStream inputStream = store.readFile(new StoredFileContentName(database.getFileHash(storedFile).getHash()).getFullFilename())) {
			StreamUtils.copy(inputStream, digest);
		}
		return digest.digest();
	}
}
