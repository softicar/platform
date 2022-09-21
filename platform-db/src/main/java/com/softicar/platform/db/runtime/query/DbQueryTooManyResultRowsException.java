package com.softicar.platform.db.runtime.query;

import com.softicar.platform.db.runtime.exception.DbException;

public class DbQueryTooManyResultRowsException extends DbException {

	private static final long serialVersionUID = 1L;

	public DbQueryTooManyResultRowsException(int actualRowCount) {

		super("Query returned %s rows but only one row was expected.", actualRowCount);
	}
}
