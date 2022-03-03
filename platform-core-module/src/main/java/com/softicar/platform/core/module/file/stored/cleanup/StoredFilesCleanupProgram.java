package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.content.database.StoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.store.StoredFileSmbContentStore;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Optional;

/**
 * TODO add javadoc
 */
@EmfSourceCodeReferencePointUuid("c748e8d1-fe5b-41dd-9d12-339177eda6e0")
public class StoredFilesCleanupProgram implements IProgram {

	@Override
	public void executeProgram() {

		StoredFileSmbContentStore store = new StoredFileSmbContentStore();

		new FileStoreTmpFolderCleaner(store).cleanAll();

		DayTime removeAtThreshold = DayTime.now();

		new StoredFileSetWithRemoveAtGarbageCollector(removeAtThreshold).collect();

		new StoredFilesWithRemoveAtGarbageCollector(removeAtThreshold).collect();

		new StoredFileStoreGarbageCollector(new StoredFileDatabase(), store).collect();

		StoredFileChunksToFileStoreMigrator.migrateAll();
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.CLEANS_FILE_SERVER_FROM_TEMPORARY_FILES);
	}
}
