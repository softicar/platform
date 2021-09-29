package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import com.softicar.platform.core.module.file.stored.content.StoredFileContentName;
import com.softicar.platform.core.module.file.stored.content.database.IStoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.hash.IStoredFileHash;
import com.softicar.platform.core.module.log.LogDb;
import com.softicar.platform.db.core.transaction.IDbTransaction;

/**
 * Removes files on the file content store that are not referenced from the
 * database anymore.
 *
 * @author Oliver Richers
 */
class StoredFileStoreGarbageCollector {

	private final IStoredFileDatabase database;
	private final IStoredFileContentStore store;
	private int count;

	public StoredFileStoreGarbageCollector(IStoredFileDatabase database, IStoredFileContentStore store) {

		this.database = database;
		this.store = store;
		this.count = 0;
	}

	public void collect() {

		Log.finfo("Removing unreferenced file hashes and associated file content...");

		for (IStoredFileHash hash: database.getUnreferencedFileHashes()) {

			try {
				collect(hash);
			} catch (Exception exception) {
				LogDb.panic(exception, "Failed to remove hash %s.", Hexadecimal.getHexStringUC(hash.getHash()));
			}
		}

		Log.finfo("%s file hashes and associated file content deleted", count);
	}

	private void collect(IStoredFileHash hash) {

		try (IDbTransaction transaction = hash.lock()) {
			if (!hash.isReferenced()) {
				StoredFileContentName name = new StoredFileContentName(hash.getHash());
				String fullFilename = name.getFullFilename();

				if (store.exists(fullFilename)) {
					Log.fverbose("deleting %s", fullFilename);
					store.removeFile(fullFilename);
				} else {
					Log.fwarning("file vanished: %s", fullFilename);
				}

				hash.delete();
				++count;
			}
			transaction.commit();
		}
	}
}
