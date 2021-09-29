package com.softicar.platform.db.runtime.exception;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Runtime exception of the database framework.
 *
 * @author Oliver Richers
 */
public class DbException extends SofticarException {

	private static final long serialVersionUID = 1L;
	private IDbTable<?, ?> table;
	private IDbTableRow<?, ?> row;

	public DbException(String format, Object...args) {

		super(format, args);
	}

	public DbException(IDbTable<?, ?> table, String format, Object...args) {

		this(table, null, format, args);
	}

	public DbException(IDbTableRow<?, ?> row, String format, Object...args) {

		this(row.table(), row, format, args);
	}

	public DbException(IDbTable<?, ?> table, IDbTableRow<?, ?> row, String format, Object...args) {

		super(table.getValueClass().getCanonicalName() + ": " + String.format(format, args));
		this.table = table;
		this.row = row;
	}

	public IDbTable<?, ?> getTable() {

		return table;
	}

	public IDbTableRow<?, ?> getRow() {

		return row;
	}
}
