package com.softicar.platform.db.sql;

/**
 * Represents a row of an {@link ISqlTable}.
 *
 * @author Oliver Richers
 */
public interface ISqlTableRow<R, P> {

	/**
	 * Returns the primary key of this table row.
	 * <p>
	 * For table rows that have a generated primary key, e.g. an auto-increment
	 * key, this method may return <i>null</i>.
	 *
	 * @return the primary key (may be null)
	 */
	P pk();

	/**
	 * Returns the table holding this row.
	 *
	 * @return the database table (never null)
	 */
	ISqlTable<R> table();
}
