package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.Collection;

/**
 * Special column handler for action columns.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IEmfDataTableActionColumnHandler<R> extends IDataTablePrefetcher<R> {

	/**
	 * Pre-fetches all data necessary to build the cells in the action column.
	 * <p>
	 * This method is called before {@link #buildCell} to give the column
	 * handler the chance to prefetch all necessary data to build the cells
	 * efficiently.
	 *
	 * @param rows
	 *            the table data rows
	 */
	@Override
	default void prefetchData(Collection<R> rows) {

		DevNull.swallow(rows);
	}

	/**
	 * Build the action cell for the given table row.
	 *
	 * @param cell
	 *            the table cell
	 * @param row
	 *            the data row
	 */
	void buildCell(IEmfDataTableActionCell<R> cell, R row);
}
