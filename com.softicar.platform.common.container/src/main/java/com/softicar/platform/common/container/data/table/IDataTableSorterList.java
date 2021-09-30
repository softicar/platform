package com.softicar.platform.common.container.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;

/**
 * A list of sorters for {@link IDataTable}.
 *
 * @author Oliver Richers
 */
public interface IDataTableSorterList<R> {

	void clear();

	boolean isEmpty();

	void addSorter(IDataTableColumn<R, ?> column, OrderDirection orderDirection);
}
