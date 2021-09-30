package com.softicar.platform.db.runtime.table.logic.save;

import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.field.DbFieldValueConsumer;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.sql.statement.ISqlInsert;
import java.util.List;

class DbTableRowInserter<R extends IDbTableRow<R, P>, P> {

	private final IDbTable<R, P> table;
	private final IDbTableKey<R, P> primaryKey;
	private final List<R> rows;
	private final int chunkSize;

	public DbTableRowInserter(IDbTable<R, P> table, List<R> rows, int chunkSize) {

		this.table = table;
		this.primaryKey = table.getPrimaryKey();
		this.rows = rows;
		this.chunkSize = chunkSize;
	}

	public void insert() {

		int index = 0;
		while (index < rows.size()) {
			int restCount = rows.size() - index;
			int count = Math.min(restCount, chunkSize);
			insertRowsChunk(rows.subList(index, index + count));
			index += count;
		}
	}

	private void insertRowsChunk(List<R> rowsToInsert) {

		if (!rowsToInsert.isEmpty()) {
			// create INSERT statement
			ISqlInsert<R> insert = table.createInsert();
			for (R row: rowsToInsert) {
				if (primaryKey.isGenerated()) {
					DbFieldValueConsumer.consumeFieldValuesDirectly(table.getDataFields(), row, insert);
				} else {
					DbFieldValueConsumer.consumeFieldValuesDirectly(table.getAllFields(), row, insert);
				}
				insert.nextRow();
			}

			// execute INSERT statement
			if (primaryKey.isGenerated()) {
				List<Integer> geratedKeys = insert.executeMultiInsert();

				// validate number of generated keys
				if (geratedKeys.size() != rowsToInsert.size()) {
					throw new DbException(
						table,
						"Number of generated keys (%s) does not match number of inserted rows (%s).",
						geratedKeys.size(),
						rowsToInsert.size());
				}

				// set generated keys of rows and put them into the cache
				IDbField<R, Integer> idField = primaryKey.getIdField();
				for (int i = 0; i < geratedKeys.size(); ++i) {
					Integer id = geratedKeys.get(i);
					R row = rowsToInsert.get(i);
					idField.setValueDirectly(row, id);
					table.getCache().put(row.pk(), row);
				}
			} else {
				insert.executeMultiInsert();

				if (primaryKey.isBase()) {
					rowsToInsert.forEach(row -> table.getCache().put(row.pk(), row));
				}
			}
		}
	}
}
