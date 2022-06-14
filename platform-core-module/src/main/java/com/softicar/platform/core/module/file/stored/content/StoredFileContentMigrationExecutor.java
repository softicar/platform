package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.core.module.file.stored.content.store.StoredFileSmbContentStore;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import com.softicar.platform.core.module.file.stored.server.AGStoredFileServer;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.stream.Collectors;

public class StoredFileContentMigrationExecutor {

	private static final String INCOMING_FILENAME_SUFFIX = ".part";
	private final ExceptionsCollector exceptionsCollector;
	private StoredFileSmbContentStore primaryStore;
	private Collection<StoredFileSmbContentStore> secondaryStores;

	public StoredFileContentMigrationExecutor() {

		this.exceptionsCollector = new ExceptionsCollector();
	}

	public void execute() {

		AGStoredFileServer primaryFileServer = AGCoreModuleInstance.getInstance().getPrimaryFileServer();
		if (primaryFileServer != null && primaryFileServer.isActive()) {
			this.primaryStore = new StoredFileSmbContentStore(primaryFileServer);
			this.secondaryStores = loadSecondaryStores();
			if (!secondaryStores.isEmpty()) {
				AGStoredFileSha1.TABLE//
					.createSelect()
					.stream()
					.map(this::getContentName)
					.forEach(this::checkAndCopyIfMissing);
			} else {
				Log.finfo("No active secondary file servers configured. Doing nothing.");
			}
		} else {
			Log.finfo("No active primary file server configured. Doing nothing.");
		}
		exceptionsCollector.throwExceptionIfNotEmpty();
	}

	private StoredFileContentName getContentName(AGStoredFileSha1 hash) {

		return new StoredFileContentName(hash.getHashString());
	}

	private void checkAndCopyIfMissing(StoredFileContentName contentName) {

		if (!primaryStore.exists(contentName.getFullFilename())) {
			try {
				Log.finfo("Cannot find %s on primary file server. Will try to copy from secondary stores.", contentName);
				createFolder(contentName);
				copyFile(contentName);
			} catch (Exception exception) {
				Log.ferror("Failed to copy %s. See exceptions at the end of the output.", contentName);
				exceptionsCollector.add(exception);
			}
		}
	}

	private void createFolder(StoredFileContentName contentName) {

		primaryStore.createFolderIfDoesNotExist(contentName.getFolderName());
	}

	private void copyFile(StoredFileContentName contentName) {

		for (StoredFileSmbContentStore store: secondaryStores) {
			if (store.exists(contentName.getFullFilename())) {
				copyFileFromStore(contentName, store);
				return;
			}
		}
		throw new RuntimeException("Failed to find %s on secondary stores.".formatted(contentName));
	}

	private void copyFileFromStore(StoredFileContentName contentName, StoredFileSmbContentStore store) {

		String incomingFilename = contentName.getFullFilename() + INCOMING_FILENAME_SUFFIX;
		try (InputStream inputStream = store.readFile(contentName.getFullFilename())) {
			try (OutputStream outputStream = primaryStore.createFile(incomingFilename)) {
				StreamUtils.copy(inputStream, outputStream);
				primaryStore.moveFile(incomingFilename, contentName.getFullFilename());
			}
		} catch (Exception exception) {
			throw new RuntimeException(//
				"Failed to copy %s from store %s to %s.".formatted(contentName, store, primaryStore),
				exception);
		}
	}

	private Collection<StoredFileSmbContentStore> loadSecondaryStores() {

		return AGStoredFileServer//
			.loadAllActive()
			.stream()
			.filter(server -> !server.isPrimary())
			.map(StoredFileSmbContentStore::new)
			.collect(Collectors.toList());
	}
}
