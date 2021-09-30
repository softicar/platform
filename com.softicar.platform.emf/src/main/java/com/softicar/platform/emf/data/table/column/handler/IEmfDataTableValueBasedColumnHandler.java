package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.filtering.IEmfDataTableColumnFilterNodeFactory;
import java.util.Collection;

/**
 * Value-based column handler interface for {@link IEmfDataTable}.
 * <p>
 * A column handler customizes the way that the table columns are sorted
 * filtered and displayed.
 * <p>
 * This is the interface for value-based column handlers as opposed to row-based
 * column handlers, implementing {@link IEmfDataTableRowBasedColumnHandler}.
 * 
 * @see IEmfDataTableRowBasedColumnHandler
 * @author Oliver Richers
 */
public interface IEmfDataTableValueBasedColumnHandler<T> {

	/**
	 * Pre-fetches all data necessary to build the cells in the given column.
	 * <p>
	 * This method is called before
	 * {@link #buildCell(IEmfDataTableCell, Object)} to give the column handler
	 * the chance to prefetch all necessary data to build the cells efficiently.
	 * 
	 * @param column
	 *            the column of the column handler (never null)
	 * @param values
	 *            the column values that will be displayed
	 */
	void prefetchData(IEmfDataTableColumn<?, T> column, Collection<T> values);

	/**
	 * Builds the given table cell using the given value.
	 * 
	 * @param cell
	 *            the table cell to build (never null)
	 * @param value
	 *            the value to display in the cell (may be null)
	 */
	void buildCell(IEmfDataTableCell<?, T> cell, T value);

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
	IEmfDataTableColumnFilterNodeFactory<T> getFilterNodeFactory(IEmfDataTableColumn<?, T> column);
}
