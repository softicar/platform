package com.softicar.platform.db.runtime.query;

import com.softicar.platform.db.runtime.table.IDbTable;

/**
 * A special query column representing a database table.
 *
 * @author Oliver Richers
 */
public interface IDbQueryTableColumn<R extends IDbQueryRow<R>, T> extends IDbQueryColumn<R, T> {

	/**
	 * Returns the table object for the values of this column.
	 *
	 * @return the table of this column values
	 * @throws ClassCastException
	 *             if this column is not a table column
	 */
	@Override
	IDbTable<T, ?> getTable();

	/**
	 * Returns true if this is table column and if the values are stub-objects.
	 *
	 * @return true if this is a table column of stubs
	 */
	@Override
	boolean isStub();
}
