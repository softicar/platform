package com.softicar.platform.db.runtime.table.logic.save;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.db.core.transaction.DbOptionalLazyTransaction;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.logic.AbstractDbTableRowModifier;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DbTableRowSaver<R extends IDbTableRow<R, P>, P> extends AbstractDbTableRowModifier<R, P> {

	private static final int DEFAULT_INSERT_CHUNK_SIZE = 512;
	private final List<R> rows;
	private boolean lazyMode;
	private int chunkSize;
	private INullaryVoidFunction finalizingBeforeSaveHook;

	public DbTableRowSaver(IDbTable<R, P> table) {

		super(table);

		this.rows = new ArrayList<>();
		this.lazyMode = false;
		this.chunkSize = DEFAULT_INSERT_CHUNK_SIZE;
		this.finalizingBeforeSaveHook = INullaryVoidFunction.NO_OPERATION;
	}

	public DbTableRowSaver<R, P> addRow(R row) {

		this.rows.add(row);
		return this;
	}

	public DbTableRowSaver<R, P> addRows(Collection<? extends R> rows) {

		this.rows.addAll(rows);
		return this;
	}

	public DbTableRowSaver<R, P> setLazyMode(boolean lazyMode) {

		this.lazyMode = lazyMode;
		return this;
	}

	public DbTableRowSaver<R, P> setChunkSize(int chunkSize) {

		if (chunkSize < 1) {
			throw new IllegalArgumentException("Chunk size must be at least 1.");
		}
		this.chunkSize = chunkSize;
		return this;
	}

	public DbTableRowSaver<R, P> setFinalizingBeforeSaveHook(INullaryVoidFunction finalizingBeforeSaveHook) {

		this.finalizingBeforeSaveHook = Objects.requireNonNull(finalizingBeforeSaveHook);
		return this;
	}

	public int save() {

		if (rows.isEmpty()) {
			return 0;
		} else {
			assertNoStubRows();
			assertNoDeletedRows();

			DbTableRowSaverSlaveList slaves = new DbTableRowSaverSlaveList(table, rows);
			try (DbOptionalLazyTransaction transaction = createOptionalLazyTransaction()) {
				slaves.forEach(it -> it.beforeSave());
				finalizingBeforeSaveHook.apply();
				slaves.forEach(it -> it.save(lazyMode, chunkSize));
				slaves.forEach(it -> it.afterSave());
				transaction.commit();
			}
			return slaves.stream().mapToInt(it -> it.getWrittenRowCount()).sum();
		}
	}

	private void assertNoStubRows() {

		if (!lazyMode) {
			for (R row: rows) {
				if (row.stub()) {
					throw new DbException(row, "Tried to save a table row stub.");
				}
			}
		}
	}

	private void assertNoDeletedRows() {

		if (table.getPrimaryKey().isGenerated()) {
			for (R row: rows) {
				if (row.impermanent() && row.pk() != null) {
					throw new DbException(row, "Tried to save a deleted table row.");
				}
			}
		}
	}
}
