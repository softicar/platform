package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Duration;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;

class FileStoreTmpFolderCleaner {

	private static final double MINIMUM_DAYS_BEFORE_REMOVAL = 3;
	private final IStoredFileContentStore store;

	public FileStoreTmpFolderCleaner(IStoredFileContentStore store) {

		this.store = store;
	}

	public void cleanAll() {

		DayTime now = DayTime.now();
		for (String filename: store.getAllFiles("/tmp/")) {
			DayTime lastModified = store.getLastModified(filename);
			int ageInDays = new Duration(lastModified, now).clamp().getDays();
			if (ageInDays > MINIMUM_DAYS_BEFORE_REMOVAL) {
				Log.finfo("removing temporary file '%s' (last modified: %s, age: %d days)", filename, lastModified, ageInDays);
				store.removeFile(filename);
			} else {
				Log.finfo("keeping temporary file '%s' for now (last modified: %s, age: %d days)", filename, lastModified, ageInDays);
			}
		}
	}
}
