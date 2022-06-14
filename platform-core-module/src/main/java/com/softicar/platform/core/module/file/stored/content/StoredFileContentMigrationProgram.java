package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.content.store.StoredFileSmbContentStore;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import com.softicar.platform.core.module.file.stored.server.AGStoredFileServer;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@EmfSourceCodeReferencePointUuid("934acfe5-f73f-414c-ae80-941f9773b0ef")
public class StoredFileContentMigrationProgram implements IProgram {

	private static final String INCOMING_FILENAME_SUFFIX = ".part";
	private final ExceptionsCollector exceptionsCollector;
	private StoredFileSmbContentStore primaryStore;
	private Collection<StoredFileSmbContentStore> secondaryStores;

	public StoredFileContentMigrationProgram() {

		this.exceptionsCollector = new ExceptionsCollector();
	}

	@Override
	public void executeProgram() {

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

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.COPIES_MISSING_FILES_FROM_SECONDARY_FILE_SERVERS_TO_THE_PRIMARY_FILE_SERVER);
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
