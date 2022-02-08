package com.softicar.platform.emf.management.importing;

import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.field.DbFieldValueConsumer;
import com.softicar.platform.db.sql.statement.ISqlInsert;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

class EmfEntitiesInserter<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;

	public EmfEntitiesInserter(IEmfTable<R, P, S> table) {

		this.table = table;
	}

	public void insertAll(Collection<R> rows) {

		try (DbTransaction transaction = new DbTransaction()) {
			ISqlInsert<R> insert = table.createInsert();
			for (R row: rows) {
				DbFieldValueConsumer.consumeFieldValuesDirectly(table.getAllFields(), row, insert);
				insert.nextRow();
			}
			insert.executeMultiInsert();
			transaction.commit();
		}
	}
}
