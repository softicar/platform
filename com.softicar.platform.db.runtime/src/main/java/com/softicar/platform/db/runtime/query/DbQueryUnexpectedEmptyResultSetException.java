package com.softicar.platform.db.runtime.query;

import com.softicar.platform.db.runtime.exception.DbException;

public class DbQueryUnexpectedEmptyResultSetException extends DbException {

	private static final long serialVersionUID = 1L;

	public DbQueryUnexpectedEmptyResultSetException() {

		super("Query unexpectedly returned no rows.");
	}
}
