package com.softicar.platform.db.runtime.query;

/**
 * Represents a row from the result of an {@link IDbQuery}.
 *
 * @author Oliver Richers
 */
public interface IDbQueryRow<R extends IDbQueryRow<R>> extends Comparable<R> {

	/**
	 * Returns the value from this row in the specified column.
	 *
	 * @param column
	 *            the column to fetch from
	 * @return the value in the given column (may be null)
	 */
	<V> V getValue(IDbQueryColumn<R, V> column);
}
