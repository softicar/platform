package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;

public class DbTableRowInvalidator<R extends IDbTableRow<R, P>, P> {

	private final R row;

	public DbTableRowInvalidator(R row) {

		this.row = row;
	}

	public void invalidate() {

		setInvalidatedFlag();
		invalidateTransientValueCache();
	}

	private void setInvalidatedFlag() {

		new DbTableRowFlagWriter(row.flags()).setInvalidated(true);
	}

	private void invalidateTransientValueCache() {

		row.table().getCache().getTransientValueCache().invalidate(row);
	}
}
