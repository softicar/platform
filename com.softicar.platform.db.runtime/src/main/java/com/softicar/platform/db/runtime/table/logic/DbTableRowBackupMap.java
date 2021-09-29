package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.core.transaction.IDbTransaction;
import com.softicar.platform.db.core.transaction.IDbTransactionListener;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * Holds the {@link DbTableRowBackup} instances of all {@link IDbTableRow}
 * objects that are modified during a {@link DbTransaction}.
 * <p>
 * Instances of this class are created by {@link DbTableRowBackuper}.
 *
 * @author Oliver Richers
 */
class DbTableRowBackupMap implements IDbTransactionListener {

	private final Map<IDbTableRow<?, ?>, DbTableRowBackup<?, ?>> map = new WeakHashMap<>();

	public <R extends IDbTableRow<R, P>, P> void storeBackupIfAbsent(R row) {

		map.computeIfAbsent(row, key -> new DbTableRowBackup<>(row));
	}

	public <R extends IDbTableRow<R, P>, P> Optional<DbTableRowBackup<R, P>> getBackup(R row) {

		@SuppressWarnings("unchecked")
		DbTableRowBackup<R, P> backup = (DbTableRowBackup<R, P>) map.get(row);
		return Optional.ofNullable(backup);
	}

	/**
	 * On commit of a nested transaction, the backups of this map are elevated
	 * to the backup map of the parent transaction.
	 * <p>
	 * The backup of a {@link IDbTableRow} is only elevated if the parent
	 * transaction does not yet contain a backup for the respective table row.
	 * This is because the backup in the parent transaction (if it exists) is
	 * the older backup and reflects the original state of the object, that is,
	 * when the parent transaction was started.
	 */
	@Override
	public void afterCommit(IDbTransaction transaction) {

		transaction//
			.getParentTransaction()
			.ifPresent(this::elevateToParent);
	}

	/**
	 * On rollback, the backups are applied to the {@link IDbTableRow} instances
	 * modified during the transaction.
	 */
	@Override
	public void afterRollback(IDbTransaction transaction) {

		transaction//
			.getData(DbTableRowBackupMap.class)
			.ifPresent(DbTableRowBackupMap::restoreAll);
	}

	protected <R extends IDbTableRow<R, ?>> boolean isChanged(R row, IDbField<R, ?> field) {

		@SuppressWarnings("unchecked")
		DbTableRowBackup<R, ?> backup = (DbTableRowBackup<R, ?>) map.get(row);
		return backup != null? backup.isChanged(field) : false;
	}

	private void restoreAll() {

		map.values().forEach(DbTableRowBackup::restore);
	}

	private void elevateToParent(IDbTransaction parentTransaction) {

		parentTransaction//
			.getOrPutData(DbTableRowBackupMap.class, DbTableRowBackupMap::new)
			.mergeChildMap(this);
	}

	private void mergeChildMap(DbTableRowBackupMap childMap) {

		for (Map.Entry<IDbTableRow<?, ?>, DbTableRowBackup<?, ?>> entry: childMap.map.entrySet()) {
			map.putIfAbsent(entry.getKey(), entry.getValue());
		}
	}
}
