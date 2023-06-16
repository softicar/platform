package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.container.map.index.IIndexMap;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTableConfiguration;
import com.softicar.platform.emf.data.table.EmfDataTableOrdering.OrderingEntry;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.data.table.column.settings.IEmfDataTableColumnSettings;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.persistence.EmfPersistentTableConfigurationRestorer;
import com.softicar.platform.emf.data.table.persistence.EmfPersistentTableConfigurationSaver;
import com.softicar.platform.emf.data.table.util.EmfDataTableRowSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * The standard implementation of an {@link IEmfDataTableController}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
class EmfDataTableController<R> implements IEmfDataTableController<R> {

	private final EmfDataTable<R> emfDataTable;
	private final IEmfDataTableConfig<R> config;
	private final Supplier<IRefreshable> rowSelectionControlElementSupplier;

	private final Map<IDataTableColumn<R, ?>, IEmfDataTableFilter<R>> columnFilters;
	private final EmfDataTableColumnMap<R> columnMap;
	private final EmfDataTableRowSet<R> selectedRows;
	private EmfDataTableOrdering<R> ordering;

	public EmfDataTableController(EmfDataTable<R> emfDataTable, IEmfDataTableConfig<R> config, Supplier<IRefreshable> rowSelectionControlElementSupplier) {

		this.emfDataTable = emfDataTable;
		this.config = config;
		this.rowSelectionControlElementSupplier = rowSelectionControlElementSupplier;
		this.columnFilters = new HashMap<>();
		this.columnMap = new EmfDataTableColumnMap<>(this, config);
		this.selectedRows = new EmfDataTableRowSet<>(columnMap.getDataColumnsInDefaultOrder());
		this.ordering = config.getOrdering();
	}

	// -------------------- table -------------------- //

	@Override
	public EmfDataTablePath getEmfDataTablePath() {

		return new EmfDataTablePath(emfDataTable);
	}

	@Override
	public IDataTable<R> getDataTable() {

		return config.getDataTable();
	}

	@Override
	public void refreshEmfDataTable() {

		emfDataTable.refresh();
	}

	// -------------------- row count and page size -------------------- //

	@Override
	public int getTotalRowCount() {

		return emfDataTable.getTotalRowCount();
	}

	@Override
	public int getPageSize() {

		return emfDataTable.getPageSize();
	}

	@Override
	public void setPageSize(int pageSize) {

		emfDataTable.setPageSize(pageSize);
	}

	@Override
	public int getDefaultPageSize() {

		return new DomPageableTableConfiguration().getPageSize();
	}

	// -------------------- column handlers -------------------- //

	@Override
	public <T> IEmfDataTableRowBasedColumnHandler<R, T> getColumnHandler(IDataTableColumn<R, T> column) {

		return config.getColumnHandler(column);
	}

	@Override
	public Optional<IEmfDataTableActionColumnHandler<R>> getActionColumnHandler() {

		return config.getActionColumnHandler();
	}

	// -------------------- column settings -------------------- //

	@Override
	public IEmfDataTableColumnSettings getColumnSettings(IDataTableColumn<R, ?> column) {

		return config.getColumnSettings(column);
	}

	@Override
	public void setColumnSettings(IDataTableColumn<R, ?> column, IEmfDataTableColumnSettings settings) {

		this.getColumnSettings(column).setAll(settings);
	}

	// -------------------- column properties -------------------- //

	@Override
	public boolean isSortable(IDataTableColumn<R, ?> column) {

		return columnMap.getEmfColumn(column).isSortable();
	}

	@Override
	public boolean isFilterable(IDataTableColumn<R, ?> column) {

		return columnMap.getEmfColumn(column).isFilterable();
	}
	// -------------------- column index -------------------- //

	@Override
	public void setCustomColumnIndexMap(IIndexMap<IDataTableColumn<R, ?>> columnIndexMap) {

		columnMap.setCustomIndexMap(columnIndexMap);
	}

	// -------------------- column retrieval -------------------- //

	@Override
	public IEmfDataTableColumn<R, ?> getEmfColumn(IDataTableColumn<R, ?> dataColumn) {

		return columnMap.getEmfColumn(dataColumn);
	}

	@Override
	public List<IEmfDataTableColumn<R, ?>> getColumnsInCustomOrder() {

		return columnMap.getEmfColumnsInCustomOrder();
	}

	@Override
	public List<IEmfDataTableColumn<R, ?>> getColumnsInDefaultOrder() {

		return columnMap.getEmfColumnsInDefaultOrder();
	}

	// -------------------- column hiding -------------------- //

	@Override
	public void setHidden(IEmfDataTableColumn<R, ?> column, boolean hidden) {

		if (hidden != column.isHidden()) {
			config.getColumnSettings(column.getDataColumn()).setHidden(hidden);
			emfDataTable.refresh();
		}
	}

	// -------------------- ordering -------------------- //

	@Override
	public EmfDataTableOrdering<R> getOrdering() {

		return ordering;
	}

	@Override
	public void setOrdering(EmfDataTableOrdering<R> ordering) {

		this.ordering = ordering;
		applyOrderingToDataTable();
		emfDataTable.refresh();
	}

	@Override
	public void addOrderByColumn(IDataTableColumn<R, ?> column, OrderDirection direction) {

		ordering.add(column, direction);
		applyOrderingToDataTable();
		emfDataTable.refresh();
	}

	@Override
	public void setOrderByColumn(IDataTableColumn<R, ?> column, OrderDirection direction) {

		ordering.clear();
		addOrderByColumn(column, direction);
	}

	@Override
	public void removeOrderByColumn(IDataTableColumn<R, ?> column) {

		ordering.remove(column);
		applyOrderingToDataTable();
		emfDataTable.refresh();
	}

	private void applyOrderingToDataTable() {

		getDataTable().getSorters().clear();
		for (OrderingEntry<R> entry: ordering.getEntries()) {
			getDataTable().getSorters().addSorter(entry.getColumn(), getEffectiveOrderDirection(entry.getColumn(), entry.getDirection()));
		}
	}

	private <T> OrderDirection getEffectiveOrderDirection(IDataTableColumn<R, T> column, OrderDirection direction) {

		boolean reverseDirection = getColumnHandler(column).isReverseOrderDirection();
		return reverseDirection? direction.getReversed() : direction;
	}

	// -------------------- row filtering -------------------- //

	@Override
	public void setColumnFilter(IDataTableColumn<R, ?> column, IEmfDataTableFilter<R> filter) {

		columnFilters.put(column, filter);
		updateFiltersAndRefresh();
	}

	@Override
	public IEmfDataTableFilter<R> getColumnFilter(IDataTableColumn<R, ?> column) {

		return columnFilters.get(column);
	}

	@Override
	public void removeColumnFilter(IDataTableColumn<R, ?> column) {

		columnFilters.remove(column);
		updateFiltersAndRefresh();
	}

	@Override
	public void removeAllColumnFiltersWithoutRefresh() {

		columnFilters.clear();
	}

	private void updateFiltersAndRefresh() {

		IDataTableFilterList<R> filterList = getDataTable().getFilters();

		filterList.clear();
		for (IEmfDataTableFilter<R> filter: columnFilters.values()) {
			filter.addTo(filterList);
		}

		emfDataTable.invalidateRowCache();
		emfDataTable.refresh();
	}

	// -------------------- row selection -------------------- //

	@Override
	public boolean isRowSelectionEnabled() {

		return config.isRowSelectionEnabled();
	}

	@Override
	public EmfDataTableRowSelectionMode getRowSelectionMode() {

		return config.getRowSelectionMode();
	}

	@Override
	public Collection<R> getSelectedRows() {

		return selectedRows.getRows();
	}

	@Override
	public void setRowSelected(IEmfDataTableRow<R> tableRow, boolean doSelect, boolean doCallbacks) {

		if (doSelect) {
			if (getRowSelectionMode() == EmfDataTableRowSelectionMode.SINGLE) {
				selectedRows.clear(); // FIXME that does not suffice
				markRowsOnPageAsUnselected();
			}
			selectedRows.addRow(tableRow.getDataRow());
		} else {
			selectedRows.removeRow(tableRow.getDataRow());
		}

		tableRow.showAsSelected(doSelect);

		if (doCallbacks) {
			invokeRowSelectionCallbacks();
		}
	}

	@Override
	public boolean isRowSelected(IEmfDataTableRow<R> tableRow) {

		R dataRow = tableRow.getDataRow();
		if (dataRow != null) {
			return selectedRows.containsRow(dataRow);
		} else {
			return false;
		}
	}

	@Override
	public void selectAllRowsOnPage() {

		markRowsOnPageAsSelected();
		invokeRowSelectionCallbacks();
	}

	@Override
	public void invertSelectionOfRowsOnPage() {

		emfDataTable.getDisplayedTableRows().forEach(row -> setRowSelected(row, !isRowSelected(row), false));
		invokeRowSelectionCallbacks();
	}

	@Override
	public void unselectAllRowsOnPage() {

		markRowsOnPageAsUnselected();
		invokeRowSelectionCallbacks();
	}

	@Override
	public void unselectAllRowsOfTable() {

		selectedRows.clear();
		unselectAllRowsOnPage();
	}

	private void markRowsOnPageAsSelected() {

		emfDataTable.getDisplayedTableRows().forEach(row -> setRowSelected(row, true, false));
	}

	private void markRowsOnPageAsUnselected() {

		emfDataTable.getDisplayedTableRows().forEach(row -> setRowSelected(row, false, false));
	}

	private void invokeRowSelectionCallbacks() {

		refreshRowSelectionControlElement();

		IEmfDataTableRowSelectionCallback<R> rowSelectionCallback = config.getRowSelectionCallback();
		if (rowSelectionCallback != null) {
			rowSelectionCallback.handleRowSelectionChange(emfDataTable);
		}
	}

	// -------------------- paging -------------------- //

	@Override
	public void handlePostPaging() {

		if (config.isRowSelectionEnabled()) {
			emfDataTable.getDisplayedTableRows().forEach(row -> row.showAsSelected(isRowSelected(row)));
			refreshRowSelectionControlElement();
		}
	}

	private void refreshRowSelectionControlElement() {

		IRefreshable rowSelectionControlElement = rowSelectionControlElementSupplier.get();
		if (rowSelectionControlElement != null) {
			rowSelectionControlElement.refresh();
		}
	}

	// -------------------- user specific table configuration -------- //

	@Override
	public void savePersistentTableConfiguration() {

		new EmfPersistentTableConfigurationSaver<>(this).save();
	}

	@Override
	public void restoreTableConfigurationAndRefresh() {

		var restored = new EmfPersistentTableConfigurationRestorer<>(this).restore();
		if (!restored) {
			emfDataTable.refresh();
		}
	}
}
