package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.db.core.SofticarSqlException;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import java.util.List;
import java.util.Objects;

/**
 * Removes expired {@link AGStoredFile} records from the database.
 * <p>
 * All {@link AGStoredFile} records that have their 'RemoveAt' field older than
 * or equal to the given threshold, will be removed.
 *
 * @author Oliver Richers
 */
class StoredFilesWithRemoveAtGarbageCollector {

	private static final int MAX_DELETE_TRY_LOOPS = 3;
	private final int totalFileCount;
	private final ISqlBooleanExpression<AGStoredFile> expiredCondition;
	private final ISqlBooleanExpression<AGStoredFile> temporaryCondition;

	public StoredFilesWithRemoveAtGarbageCollector(DayTime removeAtThreshold) {

		Objects.requireNonNull(removeAtThreshold);
		totalFileCount = AGStoredFile.TABLE.countAll();
		expiredCondition = AGStoredFile.REMOVE_AT.isLessEqual(removeAtThreshold);
		temporaryCondition = AGStoredFile.REMOVE_AT.isNotNull();
	}

	public void collect() {

		printInfoAboutFileCount();
		deleteFiles();
	}

	private void printInfoAboutFileCount() {

		int temporaryFileCount = AGStoredFile.TABLE.countAll(temporaryCondition);
		Log.finfo("%s/%s files are temporary.", temporaryFileCount, totalFileCount);

		int expiredFileCount = AGStoredFile.TABLE.countAll(temporaryCondition.and(expiredCondition));
		Log.finfo("%s/%s temporary files are expired and will be removed.", expiredFileCount, temporaryFileCount);
	}

	private void deleteFiles() {

		for (int i = 0; i < MAX_DELETE_TRY_LOOPS; ++i) {
			if (tryToDeleteFiles()) {
				return;
			}
		}

		deleteFilesOrThrow();
	}

	private boolean tryToDeleteFiles() {

		try {
			deleteFilesOrThrow();
			return true;
		} catch (SofticarSqlException exception) {
			exception.printStackTrace();
			return false;
		}
	}

	private void deleteFilesOrThrow() {

		ExceptionsCollector exceptionsCollector = new ExceptionsCollector();

		List<AGStoredFile> filesToDelete = AGStoredFile.createSelect().where(temporaryCondition.and(expiredCondition)).list();
		Log.finfo("Trying to delete %s stored files...", filesToDelete.size());
		for (AGStoredFile file: filesToDelete) {
			try {
				file.delete();
			} catch (Exception exception) {
				exceptionsCollector.add(new SofticarException(exception, "Failed to delete stored file %s.", file.getId()));
			}
		}
		Log.finfo("%s stored files deleted.", filesToDelete.size());

		exceptionsCollector.throwIfNotEmpty();
	}
}
