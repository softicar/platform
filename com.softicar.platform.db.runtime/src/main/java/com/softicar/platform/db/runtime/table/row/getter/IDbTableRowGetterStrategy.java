package com.softicar.platform.db.runtime.table.row.getter;

import java.util.Map;
import java.util.Set;

/**
 * This interface defines the strategy how to load table rows by primary key.
 *
 * @author Oliver Richers
 */
public interface IDbTableRowGetterStrategy<R, P> {

	/**
	 * Loads the table rows with the given primary keys.
	 * <p>
	 * The returned map may not contain rows that do not match the given keys.
	 * That is, while the method is free to load more rows that requested, the
	 * returned map may not contain those superfluous rows.
	 * <p>
	 * Of course, if no row for a given key exists, the returned map should not
	 * contain a respective value.
	 * <p>
	 * This method shall not be called with an empty set of keys, that is, the
	 * implementation of this method does not have to check for such a case.
	 *
	 * @param primaryKeys
	 *            the primary keys to load (never null)
	 * @return a map containing all matching rows (never null)
	 */
	Map<P, R> loadRowsByPrimaryKey(Set<P> primaryKeys);
}
