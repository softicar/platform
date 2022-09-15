package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.cleanup.IStoredFileSetGarbageCollectorQuery.IRow;
import com.softicar.platform.core.module.file.stored.set.AGStoredFileSet;
import com.softicar.platform.core.module.file.stored.set.AGStoredFileSetItem;
import com.softicar.platform.db.core.SofticarSqlException;
import com.softicar.platform.db.core.transaction.DbTransaction;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Removes garbage {@link AGStoredFileSet} and {@link AGStoredFileSetItem}
 * records from the database.
 * <p>
 * An {@link AGStoredFileSet} and all {@link AGStoredFileSetItem} related to it
 * are considered garbage if all referenced {@link AGStoredFile} records have
 * their 'RemoveAt' field older than or equal to the given threshold.
 *
 * @author Alexander Schmidt
 */
public class StoredFileSetWithRemoveAtGarbageCollector {

	private final DayTime removeAtThreshold;

	public StoredFileSetWithRemoveAtGarbageCollector(DayTime removeAtThreshold) {

		this.removeAtThreshold = Objects.requireNonNull(removeAtThreshold);
	}

	public void collect() {

		tryToDeleteFileSets();
	}

	private void tryToDeleteFileSets() {

		try {
			deleteFilesOrThrow();
		} catch (SofticarSqlException exception) {
			exception.printStackTrace();
		}
	}

	private void deleteFilesOrThrow() {

		Set<AGStoredFileSet> fileSetsToDelete = loadFileSetsToDelete();
		Log.finfo("Trying to delete %s stored file sets...", fileSetsToDelete.size());
		for (AGStoredFileSet fileSet: fileSetsToDelete) {
			try (DbTransaction transaction = new DbTransaction()) {
				deleteFileSetItems(fileSet);
				fileSet.delete();
				transaction.commit();
			} catch (Exception exception) {
				throw new SofticarDeveloperException(exception, "Failed to delete stored file set %s.", fileSet.getId());
			}
		}
		Log.finfo("%s stored file sets deleted.", fileSetsToDelete.size());
	}

	private Set<AGStoredFileSet> loadFileSetsToDelete() {

		return IStoredFileSetGarbageCollectorQuery.FACTORY//
			.createQuery()
			.setRemoveAtThreshold(removeAtThreshold)
			.stream()
			.map(IRow::getFileSet)
			.collect(Collectors.toSet());
	}

	private void deleteFileSetItems(AGStoredFileSet fileSet) {

		AGStoredFileSetItem.TABLE.createDelete().where(AGStoredFileSetItem.FILE_SET.isEqual(fileSet)).execute();
	}
}
