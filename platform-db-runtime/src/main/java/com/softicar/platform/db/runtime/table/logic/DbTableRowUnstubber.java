package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.table.DbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;

public class DbTableRowUnstubber<R extends IDbTableRow<R, P>, P> {

	private final DbTable<R, P> table;
	private final Collection<R> stubs;

	public DbTableRowUnstubber(DbTable<R, P> table, Collection<R> stubs) {

		this.table = table;
		this.stubs = stubs;
	}

	public void unstub() {

		new DbTableRowLoader<>(table)//
			.addRows(stubs.stream().filter(row -> row != null && (row.stub() || row.invalidated())))
			.reload();
	}
}
