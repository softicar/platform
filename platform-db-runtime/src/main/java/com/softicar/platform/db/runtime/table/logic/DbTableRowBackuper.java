package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Creates backups of the given {@link IDbTableRow} instances.
 *
 * @author Oliver Richers
 */
public class DbTableRowBackuper<R extends IDbTableRow<R, P>, P> {

	private final Collection<? extends R> rows;

	public DbTableRowBackuper(R row) {

		this.rows = Collections.singleton(row);
	}

	public DbTableRowBackuper(Collection<? extends R> rows) {

		this.rows = rows;
	}

	/**
	 * Creates backups of the given {@link IDbTableRow} instances in the current
	 * {@link DbTransaction} object.
	 * <p>
	 * Backups are only created for a table row if no backup was created yet in
	 * the current transaction. This is because only the oldest backup reflects
	 * the original state of the object, that is, when the transaction was
	 * started.
	 */
	public void backupForRollback() {

		DbConnections//
			.getOrPutTransactionData(DbTableRowBackupMap.class, DbTableRowBackupMap::new)
			.ifPresent(this::storeBackups);
	}

	private void storeBackups(DbTableRowBackupMap backupMap) {

		rows//
			.stream()
			.filter(Objects::nonNull)
			.forEach(backupMap::storeBackupIfAbsent);
	}
}
