package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.filtering.IEmfDataTableColumnFilterNodeFactory;
import com.softicar.platform.emf.data.table.column.handler.filtering.IEmfDataTableRowBasedColumnFilterNodeFactory;
import java.util.Collection;

/**
 * Row-based column handler interface for {@link IEmfDataTable}.
 * <p>
 * A column handler customizes the way that the table columns are sorted
 * filtered and displayed.
 * <p>
 * This is the interface for row-based column handlers as opposed to value-based
 * column handlers, implementing {@link IEmfDataTableValueBasedColumnHandler}.
 *
 * @see IEmfDataTableValueBasedColumnHandler
 * @author Oliver Richers
 */
public interface IEmfDataTableRowBasedColumnHandler<R, T> {

	/**
	 * Pre-fetches all data necessary to build the cells in the given column.
	 * <p>
	 * This method is called before {@link #buildCell} to give the column
	 * handler the chance to prefetch all necessary data to build the cells
	 * efficiently.
	 *
	 * @param column
	 *            the column of the column handler (never null)
	 * @param dataRows
	 *            the data rows that will be displayed (never null)
	 */
	void prefetchData(IEmfDataTableColumn<R, T> column, Collection<R> dataRows);

	/**
	 * Builds the given table cell using the given data row.
	 *
	 * @param cell
	 *            the table cell to build (never null)
	 * @param dataRow
	 *            the data row providing the data for the cell (never null)
	 */
	void buildCell(IEmfDataTableCell<R, T> cell, R dataRow);

	/**
	 * Returns whether the given column is sortable or not.
	 *
	 * @param column
	 *            the column to sort by (never null)
	 * @return <i>true</i> if the given column is sortable, <i>false</i>
	 *         otherwise
	 */
	boolean isSortable(IEmfDataTableColumn<?, T> column);

	/**
	 * Returns whether the given column shall be sorted in reverse order.
	 * <p>
	 * Usually this method returns <i>false</i>.
	 *
	 * @return <i>true</i> if the given column shall be sorted in reverse order,
	 *         <i>false</i> otherwise
	 */
	boolean isReverseOrderDirection();

	/**
	 * Returns an instance of {@link IEmfDataTableColumnFilterNodeFactory} for
	 * the given column.
	 * <p>
	 * If this column cannot be filtered, this method returns null.
	 * <p>
	 * If you are implementing a custom column handler, just make your handler
	 * implement the {@link IEmfDataTableColumnFilterNodeFactory} interface and
	 * let this method return <b>this</b>.
	 *
	 * @param column
	 *            the column to filter by (never null)
	 * @return the filter node factory or null
	 */
	IEmfDataTableRowBasedColumnFilterNodeFactory<R> getFilterNodeFactory(IEmfDataTableColumn<R, T> column);
}
