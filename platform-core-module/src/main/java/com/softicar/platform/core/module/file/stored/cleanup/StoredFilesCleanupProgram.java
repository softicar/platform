package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.content.database.StoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.store.StoredFileContentStores;
import com.softicar.platform.core.module.program.IProgram;
import java.util.Optional;

/**
 * Performs cleanup operations for {@link AGStoredFile} and relates tables, as
 * well as related file stores.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@SourceCodeReferencePointUuid("c748e8d1-fe5b-41dd-9d12-339177eda6e0")
public class StoredFilesCleanupProgram implements IProgram {

	@Override
	public void executeProgram() {

		var removeAtThreshold = DayTime.now();
		var storedFileDatabase = new StoredFileDatabase();
		var primaryContentStore = StoredFileContentStores.getPrimaryContentStore();

		primaryContentStore.ifPresent(store -> new FileStoreTmpFolderCleaner(store).cleanAll());

		new StoredFileSetWithRemoveAtGarbageCollector(removeAtThreshold).collect();

		new StoredFilesWithRemoveAtGarbageCollector(removeAtThreshold).collect();

		primaryContentStore.ifPresent(store -> new StoredFileStoreGarbageCollector(storedFileDatabase, store).collect());

		primaryContentStore.ifPresent(store -> StoredFileChunksToFileStoreMigrator.migrateAll(storedFileDatabase, store));
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.CLEANS_FILE_REPOSITORIES_FROM_TEMPORARY_FILES);
	}
}
