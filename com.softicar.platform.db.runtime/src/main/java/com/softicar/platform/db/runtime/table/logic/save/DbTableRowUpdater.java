package com.softicar.platform.db.runtime.table.logic.save;

import com.softicar.platform.db.runtime.exception.DbConcurrencyException;
import com.softicar.platform.db.runtime.field.DbFieldValueConsumer;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.db.sql.statement.ISqlUpdate;
import java.util.List;

class DbTableRowUpdater<R extends IDbTableRow<R, P>, P> {

	private final IDbTable<R, P> table;
	private final IDbTableKey<R, P> primaryKey;
	private final List<R> rows;

	public DbTableRowUpdater(IDbTable<R, P> table, List<R> rows) {

		this.table = table;
		this.primaryKey = table.getPrimaryKey();
		this.rows = rows;
	}

	public void update() {

		// check for degenerated table
		if (table.getDataFields().isEmpty()) {
			// table has no data fields, an UPDATE is impossible
			return;
		}

		for (R row: rows) {
			// create update statement
			ISqlUpdate<R> update = table.createUpdate();
			DbFieldValueConsumer.consumeFieldValuesDirectly(table.getDataFields(), row, update);
			primaryKey.addKeyCondition(update, row.pk());

			// execute statement
			if (update.execute() != 1) {
				// check if object still exists
				ISqlSelect<R> select = table.createSelect();
				primaryKey.addKeyCondition(select, row.pk());
				if (select.count() == 0) {
					// object has been deleted
					throw new DbConcurrencyException(row);
				}
			}
		}
	}
}
