package com.softicar.platform.db.runtime.exception;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * A special {@link DbException} indicating concurrent write operations.
 *
 * @author Oliver Richers
 */
public class DbConcurrencyException extends DbException {

	private static final long serialVersionUID = 1L;

	public DbConcurrencyException(IDbTableRow<?, ?> row) {

		super(row, "Concurrent write operations on table row.");
	}
}
