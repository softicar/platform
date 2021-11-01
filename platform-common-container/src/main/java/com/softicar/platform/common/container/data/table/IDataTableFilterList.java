package com.softicar.platform.common.container.data.table;

import java.util.Collection;

/**
 * A list of filters for {@link IDataTable}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IDataTableFilterList<R> extends IDataTableFilter {

	/**
	 * Returns a collection of all sub-filters in this filter list.
	 *
	 * @return unmodifiable collection of sub-filters (never null)
	 */
	Collection<? extends IDataTableFilter> getFilters();

	/**
	 * Returns the boolean filter list operator.
	 *
	 * @return the filter list operator (never null)
	 */
	DataTableFilterListOperator getFilterOperator();

	/**
	 * Removes all sub-filters from this filter list.
	 */
	void clear();

	/**
	 * Returns whether this filter list is empty or not.
	 *
	 * @return <i>true</i> if no sub-filters exist
	 */
	boolean isEmpty();

	/**
	 * Adds a new filter sub-list to this filter list.
	 *
	 * @param operator
	 *            the filter list operator
	 */
	IDataTableFilterList<R> addFilterList(DataTableFilterListOperator operator);

	/**
	 * Adds a new value filter to this filter list.
	 *
	 * @param column
	 *            the filter column
	 * @param operator
	 *            the filter operator
	 * @param value
	 *            the filter value (never null)
	 */
	<V> void addValueFilter(IDataTableColumn<R, V> column, DataTableValueFilterOperator operator, V value);

	/**
	 * Adds a new collection filter to this filter list.
	 *
	 * @param column
	 *            the filter column
	 * @param operator
	 *            the filter operator
	 * @param values
	 *            the filter value collection (never null)
	 */
	<V> void addCollectionFilter(IDataTableColumn<R, V> column, DataTableCollectionFilterOperator operator, Collection<? extends V> values);

	/**
	 * Adds a new string filter to this filter list.
	 *
	 * @param column
	 *            the filter column
	 * @param operator
	 *            the filter operator
	 * @param value
	 *            the filter string (never null)
	 */
	void addStringFilter(IDataTableColumn<R, String> column, DataTableStringFilterOperator operator, String value);
}
