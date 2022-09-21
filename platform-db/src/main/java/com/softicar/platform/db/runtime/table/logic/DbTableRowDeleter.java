package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.transaction.DbOptionalLazyTransaction;
import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTable;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.listener.DbTableRowCommitNotifier;
import com.softicar.platform.db.runtime.table.listener.DbTableRowNotificationType;
import com.softicar.platform.db.runtime.table.listener.IDbTableListener;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.sql.statement.ISqlDelete;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DbTableRowDeleter<R extends IDbTableRow<R, P>, P> extends AbstractDbTableRowModifier<R, P> {

	private final List<R> rows;

	public DbTableRowDeleter(IDbTable<R, P> table) {

		super(table);

		this.rows = new ArrayList<>();
	}

	public DbTableRowDeleter<R, P> addRow(R row) {

		this.rows.add(row);
		return this;
	}

	public DbTableRowDeleter<R, P> addRows(Collection<? extends R> rows) {

		this.rows.addAll(rows);
		return this;
	}

	public void delete() {

		try (DbOptionalLazyTransaction transaction = createOptionalLazyTransaction()) {
			assertNoStubRows();
			assertNoDeletedRows();

			deleteRows();
			deleteBases();

			transaction.commit();
		}
	}

	private void assertNoStubRows() {

		for (R row: rows) {
			if (row.stub()) {
				throw new DbException(row, "Tried to delete a table row stub.");
			}
		}
	}

	private void assertNoDeletedRows() {

		if (table.getPrimaryKey().isGenerated()) {
			for (R row: rows) {
				if (row.impermanent() && row.pk() != null) {
					throw new DbException(row, "Tried to delete an already deleted table row.");
				}
			}
		}
	}

	private void deleteRows() {

		if (!rows.isEmpty()) {
			// send pre-delete notification
			sendNotification(IDbTableListener::beforeDelete, rows);

			// execute delete
			backupRowsForRollback(rows);
			rows.forEach(this::deleteRow);

			// send post-delete notification
			sendNotification(IDbTableListener::afterDelete, rows);
			DbTableRowCommitNotifier.addNotification(table, DbTableRowNotificationType.DELETE, rows);
		}
	}

	private void deleteRow(R row) {

		ISqlDelete<R> delete = table.createDelete();
		table.getPrimaryKey().addKeyCondition(delete, row.pk());
		if (delete.execute() == 1) {
			new DbTableRowFlagWriter(row).clearFlags();
			new DbTableRowFlagWriter(row).setImpermanent(true);
		} else {
			throw new DbException(row, "Tried to delete a non-persistent table row.");
		}
	}

	private void deleteBases() {

		table.ifSubObjectTable(table -> deleteBases(table));
	}

	private <B extends IDbEntity<B, Q>, Q> void deleteBases(IDbSubObjectTable<? super R, B, Q> table) {

		IDbForeignRowField<? super R, B, Q> baseField = table.getPrimaryKeyField();

		Collection<B> basesToDelete = rows//
			.stream()
			.map(baseField::getValueDirectly)
			.filter(base -> !base.stub())
			.collect(Collectors.toList());

		new DbTableRowDeleter<>(table.getBaseTable())//
			.addRows(basesToDelete)
			.delete();
	}
}
