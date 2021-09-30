package com.softicar.platform.db.runtime.table.logic.save;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.table.statements.DbTableKeyIsInExpression;
import com.softicar.platform.db.sql.statement.builder.SqlSelectBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class DbTableRowInsertOrUpdater<R extends IDbTableRow<R, P>, P> {

	private final IDbTable<R, P> table;
	private final IDbTableKey<R, P> primaryKey;
	private final List<R> rowsToInsert;
	private final List<R> rowsToUpdate;
	private final int chunkSize;

	public DbTableRowInsertOrUpdater(IDbTable<R, P> table, int chunkSize) {

		this.table = table;
		this.primaryKey = table.getPrimaryKey();
		this.rowsToInsert = new ArrayList<>();
		this.rowsToUpdate = new ArrayList<>();
		this.chunkSize = chunkSize;
	}

	public void insertOrUpdate(Collection<R> rows) {

		if (primaryKey.isGenerated()) {
			for (R row: rows) {
				if (row.pk() != null) {
					rowsToUpdate.add(row);
				} else {
					rowsToInsert.add(row);
				}
			}
		} else {
			Set<P> existingRows = new TreeSet<>(primaryKey);
			SqlSelectBuilder selectBuilder = new SqlSelectBuilder(table, primaryKey.getFields());
			selectBuilder.getWhereBuilder().where(new DbTableKeyIsInExpression<>(table).addKeysFromRows(rows));
			try (DbResultSet resultSet = new DbStatement()//
				.addTable(table)
				.addText(selectBuilder.getSelectText().toString())
				.addParameters(selectBuilder.getParameters())
				.executeQuery()) {
				while (resultSet.next()) {
					existingRows.add(primaryKey.getFromResultSet(resultSet, 1));
				}
			}
			for (R row: rows) {
				if (existingRows.contains(row.pk())) {
					rowsToUpdate.add(row);
				} else {
					rowsToInsert.add(row);
				}
			}
		}

		new DbTableRowInserter<>(table, rowsToInsert, chunkSize).insert();
		new DbTableRowUpdater<>(table, rowsToUpdate).update();
	}
}
