package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.container.comparator.ByteArrayComparator;
import com.softicar.platform.common.container.map.MapFactory;
import com.softicar.platform.common.container.set.SetFactory;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Duration;
import com.softicar.platform.common.string.formatting.MemoryFormatting;
import com.softicar.platform.core.module.file.stored.content.StoredFileContentName;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import java.util.Map;
import java.util.Set;

class UnreferencedStoredFileCleaner {

	static final int MINIMUM_DAYS_BEFORE_REMOVAL = 7;
	private static final long MINIMUM_AGE_BEFORE_REMOVAL = MINIMUM_DAYS_BEFORE_REMOVAL * 24 * 60 * 60;
	private final IStoredFileContentStore store;
	private final Set<byte[]> filesInDatabase;
	private final Map<byte[], StoredFileContentName> filesOnFileStore;

	public UnreferencedStoredFileCleaner(IStoredFileContentStore store) {

		this.store = store;
		this.filesOnFileStore = MapFactory.createTreeMap(ByteArrayComparator.get());
		this.filesInDatabase = SetFactory.createTreeSet(ByteArrayComparator.get());
	}

	public void cleanAll() {

		collectFilesInDatabase();
		collectFilesOnFileStore();

		filesOnFileStore.keySet().removeAll(filesInDatabase);
		Log.finfo("%s unreferenced files:", filesOnFileStore.size());
		for (StoredFileContentName contentName: filesOnFileStore.values()) {

			DayTime lastModified = store.getLastModified(contentName.getFullFilename());
			Duration age = new Duration(lastModified, DayTime.now()).clamp();
			if (age.getTotalSeconds() > MINIMUM_AGE_BEFORE_REMOVAL) {
				Log.finfo("removing %s (last modified: %s", contentName.getFullFilename(), lastModified);
				store.deleteFile(contentName.getFullFilename());
			} else {
				Log.finfo("keeping %s for now (last modified: %s, age: %s days)", contentName.getFullFilename(), lastModified, age.getDays());
			}
		}
	}

	private void collectFilesOnFileStore() {

		Log.finfo("collecting files on file store...");
		for (String filename: store.getAllFilePaths()) {
			DayTime lastModified = store.getLastModified(filename);
			if (StoredFileContentName.isCorrectFilename(filename)) {
				StoredFileContentName contentName = StoredFileContentName.createFromFilename(filename);
				filesOnFileStore.put(contentName.getHash(), contentName);
			} else {
				Log
					.fwarning(
						"skipping file with invalid filename '%s' (last modified: %s, age: %.1f days)",
						filename,
						lastModified,
						new Duration(lastModified, DayTime.now()).getDays());
			}
		}
		Log.finfo("done, found %d files on file store", filesOnFileStore.size());
	}

	private void collectFilesInDatabase() {

		Log.finfo("collecting files in database...");
		long totalSize = 0;
		for (AGStoredFileSha1 sha1: AGStoredFileSha1.TABLE.loadAll()) {
			filesInDatabase.add(sha1.getHash());
			totalSize += sha1.getSize();
		}
		Log.finfo("done, found %d files in database (%s)", filesInDatabase.size(), MemoryFormatting.formatMemory(totalSize, 1));
	}
}
