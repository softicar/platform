package com.softicar.platform.common.container.data.table;

import java.util.Collection;
import java.util.List;

/**
 * A data container in tabular form.
 *
 * @author Oliver Richers
 */
public interface IDataTable<R> extends IDataTableRowMethods<R> {

	/**
	 * Returns a {@link DataTableIdentifier} for this {@link IDataTable}.
	 *
	 * @return the {@link DataTableIdentifier} (never <i>null</i>)
	 */
	default DataTableIdentifier getIdentifier() {

		return new DataTableIdentifier("");
	}

	// -------------------- columns -------------------- //

	/**
	 * Returns all columns of this table.
	 *
	 * @return list of all columns
	 */
	List<? extends IDataTableColumn<R, ?>> getTableColumns();

	/**
	 * Returns a list of all distinct values in the given column.
	 *
	 * @param column
	 *            the table column to check
	 * @return all distinct values
	 */
	default <V> Collection<V> getDistinctColumnValues(IDataTableColumn<R, V> column) {

		return getDistinctColumnValues(column, 0);
	}

	/**
	 * Returns a limited list of all distinct values in the given column.
	 *
	 * @param column
	 *            the table column to check
	 * @param limit
	 *            the maximum number of distinct values to return or <i>zero</i>
	 *            or negative for no limit
	 * @return limited list of all distinct values
	 */
	<V> Collection<V> getDistinctColumnValues(IDataTableColumn<R, V> column, int limit);

	// -------------------- filtering and sorting -------------------- //

	/**
	 * Returns the list of active filters.
	 *
	 * @return current filter list
	 */
	IDataTableFilterList<R> getFilters();

	/**
	 * Returns the list of active sorters.
	 *
	 * @return current sorter list
	 */
	IDataTableSorterList<R> getSorters();

	// -------------------- miscellaneous -------------------- //

	/**
	 * Resets all internal caches of this table.
	 * <p>
	 * An implementation of {@link IDataTable} might optimize operations by
	 * caching important data. By calling this method, all cached values will be
	 * invalidated, so they will be freshly reloaded when requested.
	 */
	default void invalidateCaches() {

		// nothing to do by default
	}
}
