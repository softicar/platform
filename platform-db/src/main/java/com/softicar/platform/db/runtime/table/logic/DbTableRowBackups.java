package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Optional;

/**
 * Utility methods concerning the backup of {@link IDbTableRow} objects during a
 * {@link DbTransaction}.
 *
 * @author Oliver Richers
 */
public class DbTableRowBackups {

	/**
	 * Checks whether the given field of the given row was changed during the
	 * current transaction.
	 * <p>
	 * Outside of a transaction this method will always return false.
	 *
	 * @param row
	 *            the table row
	 * @param field
	 *            the field of the table row
	 * @return true if the field was changed, false otherwise
	 */
	public static <R extends IDbTableRow<R, ?>> boolean isChanged(R row, IDbField<R, ?> field) {

		return getBackupMap()//
			.map(map -> map.isChanged(row, field))
			.orElse(false);
	}

	public static <R extends IDbTableRow<R, P>, P> Optional<DbTableRowBackup<R, P>> getBackup(R row) {

		return getBackupMap()//
			.flatMap(map -> map.getBackup(row));
	}

	private static Optional<DbTableRowBackupMap> getBackupMap() {

		return DbConnections.getTransactionData(DbTableRowBackupMap.class);
	}
}
