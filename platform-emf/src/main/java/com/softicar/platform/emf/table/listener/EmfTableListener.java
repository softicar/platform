package com.softicar.platform.emf.table.listener;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.runtime.table.listener.IDbTableListener;
import com.softicar.platform.db.runtime.table.listener.IDbTableRowNotificationSet;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.emf.log.EmfChangeTracker;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.EmfValidationException;
import com.softicar.platform.emf.validation.result.EmfValidationResult;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public class EmfTableListener<R extends IEmfTableRow<R, ?>> implements IDbTableListener<R> {

	private final IEmfTable<R, ?, ?> table;

	public EmfTableListener(IEmfTable<R, ?, ?> table) {

		this.table = table;
	}

	@Override
	public void beforeSave(Collection<R> rows) {

		// update transaction fields of entities
		table//
			.getTransactionAttribute()
			.ifPresent(attribute -> attribute.updateTransactionFields(rows));

		// track which fields where changed
		DbConnections//
			.getOrPutTransactionData(EmfChangeTracker.class, EmfChangeTracker::new)
			.ifPresent(tracker -> tracker.trackChanges(rows));

		executeSaveHooks(hook -> hook.beforeSave(rows));
	}

	@Override
	public void afterSave(Collection<R> rows) {

		executeSaveHooks(hook -> hook.afterSave(rows));
	}

	@Override
	public void beforeDelete(Collection<R> rows) {

		executeDeleteHooks(hook -> hook.beforeDelete(rows));
	}

	@Override
	public void afterDelete(Collection<R> rows) {

		executeDeleteHooks(hook -> hook.afterDelete(rows));
	}

	@Override
	public void beforeCommit(IDbTableRowNotificationSet<R> notifications) {

		validateEntities(notifications.getSavedRows());
		writeLogs(notifications.getSavedRows());
		executeCommitHooks(hook -> hook.beforeCommit(notifications));
	}

	@Override
	public void afterCommit(IDbTableRowNotificationSet<R> notifications) {

		notifyRefreshBus(notifications);
		executeCommitHooks(hook -> hook.afterCommit(notifications));
	}

	private void notifyRefreshBus(IDbTableRowNotificationSet<R> notifications) {

		Optional//
			.ofNullable(CurrentDomDocument.get())
			.map(IDomDocument::getRefreshBus)
			.ifPresent(refreshBus -> notifications.getChangedRows().forEach(refreshBus::setChanged));
	}

	private void executeSaveHooks(Consumer<IEmfSaveHook<R>> callback) {

		table//
			.getEmfTableConfiguration()
			.getSaveHooks()
			.forEach(callback);
	}

	private void executeDeleteHooks(Consumer<IEmfDeleteHook<R>> callback) {

		table//
			.getEmfTableConfiguration()
			.getDeleteHooks()
			.forEach(callback);
	}

	private void executeCommitHooks(Consumer<IEmfCommitHook<R>> callback) {

		table//
			.getEmfTableConfiguration()
			.getCommitHooks()
			.forEach(callback);
	}

	private void writeLogs(Collection<R> rows) {

		if (EmfTableListenerSettings.isLoggingEnabled()) {
			table//
				.getChangeLoggers()
				.forEach(logger -> logger.logChange(rows));
		}
	}

	private void validateEntities(Collection<R> rows) {

		for (R row: rows) {
			IEmfValidationResult validationResult = new EmfValidationResult();
			row.validate(validationResult);
			if (validationResult.hasErrors()) {
				throw new EmfValidationException(validationResult);
			}
		}
	}
}
