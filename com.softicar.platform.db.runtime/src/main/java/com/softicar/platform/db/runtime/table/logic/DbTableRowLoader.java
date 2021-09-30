package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.transaction.DbOptionalLazyTransaction;
import com.softicar.platform.db.runtime.cache.IDbTableRowCache;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.table.statements.DbTableKeyIsInExpression;
import com.softicar.platform.db.runtime.table.utils.DbTableRowMap;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public class DbTableRowLoader<R extends IDbTableRow<R, P>, P> extends AbstractDbTableRowModifier<R, P> {

	private final IDbTableRowCache<R, P> cache;
	private final IDbTableKey<R, P> primaryKey;
	private final Map<P, R> rowsToLoad;
	private final Map<P, R> loadedRows;
	private SqlSelectLock lock;

	public DbTableRowLoader(IDbTable<R, P> table) {

		super(table);

		this.cache = table.getCache();
		this.primaryKey = table.getPrimaryKey();
		this.rowsToLoad = new DbTableRowMap<>(table);
		this.loadedRows = new DbTableRowMap<>(table);
	}

	// -------------------- setter -------------------- //

	public DbTableRowLoader<R, P> addKey(P key) {

		put(key, null);
		return this;
	}

	public DbTableRowLoader<R, P> addRow(R row) {

		put(row.pk(), row);
		return this;
	}

	public DbTableRowLoader<R, P> addKeys(Stream<P> keys) {

		keys.forEach(this::addKey);
		return this;
	}

	public DbTableRowLoader<R, P> addKeys(Collection<P> keys) {

		keys.forEach(this::addKey);
		return this;
	}

	public DbTableRowLoader<R, P> addRows(Stream<R> rows) {

		rows.forEach(this::addRow);
		return this;
	}

	public DbTableRowLoader<R, P> setLock(SqlSelectLock lock) {

		this.lock = lock;
		return this;
	}

	private void put(P key, R row) {

		validateKeyAndRow(key, row);
		this.rowsToLoad.put(key, row != null? row : cache.getSimple(key));
	}

	private R validateKeyAndRow(P key, R row) {

		// test key itself
		if (key == null) {
			throw new DbException(table, row, "Tried to load a table row for an undefined primary key.");
		}

		// test key fields
		if (primaryKey.getFields().size() > 1) {
			for (IDbField<R, ?> field: primaryKey.getFields()) {
				if (primaryKey.getValue(field, key) == null) {
					throw new DbException(table, row, "Tried to load a table row for an invalid primary key.");
				}
			}
		}

		// check row in cache
		R cachedRow = cache.getSimple(key);
		if (row != null && row != cachedRow) {
			throw new DbException(row, "Tried to load a table row that belongs to another thread.");
		}

		return cachedRow;
	}

	// -------------------- loading -------------------- //

	public boolean reload() {

		return loadAsMap().size() == rowsToLoad.size();
	}

	public Map<P, R> loadAsMap() {

		validateLock();
		try (DbOptionalLazyTransaction transaction = createOptionalLazyTransaction()) {
			backupRowsForRollback();
			invalidateRows();
			executeSelect();
			setImpermanentFlagForMissingRowsAfterLoading();
			unsetStubFlag();
			transaction.commit();
		}
		return loadedRows;
	}

	/**
	 * Checks whether a transaction has been started if locking is required.
	 */
	private void validateLock() {

		if (lock != null) {
			if (!DbConnections.isTransactionStarted()) {
				throw new DbException(table, "Tried to lock a table row without starting a transaction.");
			}
		}
	}

	private void backupRowsForRollback() {

		backupRowsForRollback(rowsToLoad.values());
	}

	/**
	 * Sets the "invalidated" flag of cached table rows.
	 */
	private void invalidateRows() {

		rowsToLoad//
			.values()
			.stream()
			.filter(row -> row != null)
			.forEach(row -> new DbTableRowFlagWriter(row).setInvalidated(true));
		table.getCache().getTransientValueCache().invalidateAll(rowsToLoad.values());
	}

	/**
	 * Sets the "impermanent" flag for all rows that were not found while
	 * loading.
	 */
	private void setImpermanentFlagForMissingRowsAfterLoading() {

		rowsToLoad//
			.values()
			.stream()
			.filter(this::isMissingRowAfterLoading)
			.forEach(row -> new DbTableRowFlagWriter(row).setImpermanent(true));
	}

	private boolean isMissingRowAfterLoading(R row) {

		return row != null && !loadedRows.containsKey(row.pk());
	}

	/**
	 * Unsets the "stub" flag of cached table rows.
	 */
	private void unsetStubFlag() {

		rowsToLoad//
			.values()
			.stream()
			.filter(row -> row != null)
			.forEach(row -> new DbTableRowFlagWriter(row).setStub(false));
	}

	/**
	 * Executes the query to load rows.
	 */
	private void executeSelect() {

		if (!rowsToLoad.isEmpty()) {
			for (R row: table//
				.createSelect(lock)
				.where(new DbTableKeyIsInExpression<>(table).addKeyValues(rowsToLoad.keySet()))
				.list()) {
				loadedRows.put(row.pk(), row);
			}
		}
	}
}
