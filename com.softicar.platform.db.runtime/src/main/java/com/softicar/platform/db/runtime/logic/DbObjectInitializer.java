package com.softicar.platform.db.runtime.logic;

import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.table.row.DbTableRowInitializer;

public class DbObjectInitializer<R extends AbstractDbObject<R>> extends DbTableRowInitializer<R, Integer> {

	public DbObjectInitializer(R row) {

		super(row);
	}

	public R initializeCopy(R source) {

		initializeFlags(DbTableRowFlag.IMPERMANENT);
		initializeDataFields(source);
		return row;
	}
}
