package com.softicar.platform.db.runtime.table.logic.save;

import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.listener.DbTableRowCommitNotifier;
import com.softicar.platform.db.runtime.table.listener.DbTableRowNotificationType;
import com.softicar.platform.db.runtime.table.listener.IDbTableListener;
import com.softicar.platform.db.runtime.table.logic.AbstractDbTableRowModifier;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagWriter;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;
import java.util.stream.Collectors;

class DbTableRowSaverSlave<R extends IDbTableRow<R, P>, P> extends AbstractDbTableRowModifier<R, P> {

	private final Collection<R> rows;
	private final Collection<R> changedRows;
	private int writtenRowCount;

	public DbTableRowSaverSlave(IDbTable<R, P> table, Collection<R> rows) {

		super(table);
		this.rows = rows;
		this.changedRows = getRowsWithPersistentChange(rows);
		this.writtenRowCount = 0;
	}

	public int getWrittenRowCount() {

		return writtenRowCount;
	}

	public void beforeSave() {

		if (!rows.isEmpty()) {
			sendNotification(IDbTableListener::beforeSave, rows);
			backupRowsForRollback(rows);
		}
	}

	public void save(boolean lazyMode, int chunkSize) {

		if (!rows.isEmpty()) {
			Collection<R> rowsToWrite = getRowsToWrite(lazyMode);
			new DbTableRowInsertOrUpdater<>(table, chunkSize).insertOrUpdate(rowsToWrite);
			this.writtenRowCount = rowsToWrite.size();
		}
	}

	public void afterSave() {

		if (!rows.isEmpty()) {
			clearRowFlags();
			sendNotification(IDbTableListener::afterSave, rows);
			DbTableRowCommitNotifier.addNotification(table, DbTableRowNotificationType.SAVE, rows);
		}
		if (!changedRows.isEmpty()) {
			DbTableRowCommitNotifier.addNotification(table, DbTableRowNotificationType.CHANGE, changedRows);
		}
	}

	private Collection<R> getRowsWithPersistentChange(Collection<R> rows) {

		return rows//
			.stream()
			.filter(row -> row.dataChanged() || row.impermanent())
			.collect(Collectors.toList());
	}

	private Collection<R> getRowsToWrite(boolean lazyMode) {

		if (lazyMode) {
			return rows//
				.stream()
				.filter(row -> row.impermanent() || row.dataChanged())
				.collect(Collectors.toList());
		} else {
			return rows;
		}
	}

	private void clearRowFlags() {

		rows.forEach(row -> new DbTableRowFlagWriter(row).clearFlags());
	}
}
