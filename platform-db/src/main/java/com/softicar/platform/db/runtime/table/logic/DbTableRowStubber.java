package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.table.DbTable;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

public class DbTableRowStubber<R extends IDbTableRow<R, P>, P> {

	private final DbTable<R, P> table;
	private final P primaryKey;

	public DbTableRowStubber(DbTable<R, P> table, P primaryKey) {

		this.table = table;
		this.primaryKey = primaryKey;
	}

	public R stub() {

		if (primaryKey == null) {
			return null;
		}

		R row = table.getCache().getSimple(primaryKey);
		if (row == null) {
			row = table//
				.getRowFactory()
				.get()
				.initializer()
				.initializeFlagsAndSetPkFields(primaryKey, DbTableRowFlag.STUB);
			table.getCache().put(row.pk(), row);
		}
		return row;
	}
}
