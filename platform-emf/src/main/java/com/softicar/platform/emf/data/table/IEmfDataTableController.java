package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.map.index.IIndexMap;
import com.softicar.platform.dom.elements.tables.scrollable.hooks.IPostPagingHook;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.data.table.column.settings.IEmfDataTableColumnSettings;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Maintains and controls the state of an {@link IEmfDataTable}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public interface IEmfDataTableController<R> extends IPostPagingHook {

	// -------------------- table -------------------- //

	/**
	 * Returns the {@link EmfDataTablePath} of the {@link IEmfDataTable} to
	 * which this {@link IEmfDataTableController} belongs.
	 *
	 * @return the {@link EmfDataTablePath} (never <i>null</i>)
	 */
	EmfDataTablePath getEmfDataTablePath();

	/**
	 * Returns the {@link IDataTable} underlying the {@link IEmfDataTable}.
	 *
	 * @return the underlying {@link IDataTable} (never <i>null</i>)
	 */
	IDataTable<R> getDataTable();

	/**
	 * Refreshes the {@link IEmfDataTable}.
	 */
	void refreshEmfDataTable();

	// -------------------- row count and page size -------------------- //

	/**
	 * Returns the total number of rows, accumulated over all pages of the
	 * {@link IEmfDataTable}.
	 *
	 * @return the total number of rows
	 */
	int getTotalRowCount();

	/**
	 * Returns the maximum number of rows that are displayed on a single page of
	 * the {@link IEmfDataTable}.
	 *
	 * @return the page size
	 */
	int getPageSize();

	/**
	 * Defines the maximum number of rows that are displayed on a single page of
	 * the {@link IEmfDataTable}.
	 *
	 * @param pageSize
	 *            the page size (greater than 0)
	 */
	void setPageSize(int pageSize);

	/**
	 * Returns the default page size of the underlying {@link IEmfDataTable}.
	 *
	 * @return the default page size (greater than 0)
	 */
	int getDefaultPageSize();

	// -------------------- column handlers -------------------- //

	<T> IEmfDataTableRowBasedColumnHandler<R, T> getColumnHandler(IDataTableColumn<R, T> column);

	Optional<IEmfDataTableActionColumnHandler<R>> getActionColumnHandler();

	// -------------------- column settings -------------------- //

	IEmfDataTableColumnSettings getColumnSettings(IDataTableColumn<R, ?> column);

	void setColumnSettings(IDataTableColumn<R, ?> column, IEmfDataTableColumnSettings settings);

	// -------------------- column properties -------------------- //

	boolean isSortable(IDataTableColumn<R, ?> column);

	boolean isFilterable(IDataTableColumn<R, ?> column);

	// -------------------- column index -------------------- //

	void setCustomColumnIndexMap(IIndexMap<IDataTableColumn<R, ?>> columnIndexMap);

	// -------------------- column retrieval -------------------- //

	IEmfDataTableColumn<R, ?> getEmfColumn(IDataTableColumn<R, ?> dataColumn);

	/**
	 * Returns all non-concealed columns in the order of the underlying data
	 * table.
	 *
	 * @return the available columns, in default order (never <i>null</i>)
	 */
	List<IEmfDataTableColumn<R, ?>> getColumnsInDefaultOrder();

	/**
	 * Returns all non-concealed columns in the order that the user configured.
	 *
	 * @return the available columns, in custom order (never <i>null</i>)
	 */
	List<IEmfDataTableColumn<R, ?>> getColumnsInCustomOrder();

	// -------------------- column hiding -------------------- //

	void setHidden(IEmfDataTableColumn<R, ?> column, boolean hidden);

	// -------------------- ordering -------------------- //

	EmfDataTableOrdering<R> getOrdering();

	void setOrdering(EmfDataTableOrdering<R> ordering);

	void addOrderByColumn(IDataTableColumn<R, ?> column, OrderDirection direction);

	void setOrderByColumn(IDataTableColumn<R, ?> column, OrderDirection direction);

	void removeOrderByColumn(IDataTableColumn<R, ?> column);

	// -------------------- row filtering -------------------- //

	void setColumnFilter(IDataTableColumn<R, ?> column, IEmfDataTableFilter<R> filter);

	IEmfDataTableFilter<R> getColumnFilter(IDataTableColumn<R, ?> column);

	void removeColumnFilter(IDataTableColumn<R, ?> column);

	void removeAllColumnFiltersWithoutRefresh();

	// -------------------- row selection -------------------- //

	boolean isRowSelectionEnabled();

	EmfDataTableRowSelectionMode getRowSelectionMode();

	Collection<R> getSelectedRows();

	// FIXME two boolean parameters is bad design; improve API
	void setRowSelected(IEmfDataTableRow<R> tableRow, boolean doSelect, boolean doCallbacks);

	boolean isRowSelected(IEmfDataTableRow<R> tableRow);

	void selectAllRowsOnPage();

	void invertSelectionOfRowsOnPage();

	void unselectAllRowsOnPage();

	void unselectAllRowsOfTable();

	// -------------------- persistent table configuration -------- //

	void savePersistentTableConfiguration();

	void restoreTableConfigurationAndRefresh();
}
