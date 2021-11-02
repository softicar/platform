package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.db.core.database.IDbDatabaseScope;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableColumnHandlerMap;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.data.table.column.settings.EmfDataTableColumnSettings;
import com.softicar.platform.emf.data.table.column.settings.EmfDataTableColumnSettingsMap;
import com.softicar.platform.emf.data.table.header.secondary.EmfDataTableExtraRowColumnGroupListAccumulator;
import com.softicar.platform.emf.data.table.header.secondary.IEmfDataTableExtraRowColumnGroupListAccumulator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

class EmfDataTableConfig<R> implements IEmfDataTableConfig<R> {

	// keep alphabetical order
	private Optional<IEmfDataTableActionColumnHandler<R>> actionColumnHandler;
	private Optional<Consumer<Collection<R>>> callbackAfterContentChange;
	private Optional<IDataTablePrefetcher<R>> callbackBeforePrefetch;
	private final EmfDataTableColumnHandlerMap<R> columnHandlerMap;
	private final EmfDataTableColumnSettingsMap<R> columnSettingsMap;
	private Supplier<IDbDatabaseScope> databaseScopeSupplier;
	private final IDataTable<R> dataTable;
	private boolean hideNavigationAtBottom;
	private boolean hideNavigationAtTop;
	private IStaticObject tableMarker;
	private IStaticObject tableDivMarker;
	private final EmfDataTableOrdering<R> ordering;
	private int pageSize;
	private final List<DomButton> rowSelectionActionButtons;
	private IEmfDataTableRowSelectionCallback<R> rowSelectionCallback;
	private EmfDataTableRowSelectionMode rowSelectionMode;
	private Optional<IEmfDataTableRowCustomizer<R>> rowCustomizer;
	private final IEmfDataTableExtraRowColumnGroupListAccumulator<R> secondaryHeaderRowAccumulator;
	private final IEmfDataTableExtraRowColumnGroupListAccumulator<R> footerRowAccumulator;

	public EmfDataTableConfig(IDataTable<R> dataTable) {

		this.actionColumnHandler = Optional.empty();
		this.callbackAfterContentChange = Optional.empty();
		this.callbackBeforePrefetch = Optional.empty();
		this.columnHandlerMap = new EmfDataTableColumnHandlerMap<>();
		this.columnSettingsMap = new EmfDataTableColumnSettingsMap<>();
		this.databaseScopeSupplier = NoOperationDatabaseScope::new;
		this.dataTable = dataTable;
		this.hideNavigationAtBottom = false;
		this.hideNavigationAtTop = true;
		this.tableMarker = EmfDataTableDivMarker.TABLE;
		this.tableDivMarker = EmfDataTableDivMarker.TABLE_DIV;
		this.ordering = new EmfDataTableOrdering<>();
		this.pageSize = DomPageableTable.DEFAULT_PAGE_SIZE;
		this.rowSelectionActionButtons = new ArrayList<>();
		this.rowSelectionMode = EmfDataTableRowSelectionMode.NONE;
		this.rowCustomizer = Optional.empty();
		this.secondaryHeaderRowAccumulator = new EmfDataTableExtraRowColumnGroupListAccumulator<>();
		this.footerRowAccumulator = new EmfDataTableExtraRowColumnGroupListAccumulator<>();
	}

	@Override
	public IDataTable<R> getDataTable() {

		return dataTable;
	}

	public void setActionColumnHandler(IEmfDataTableActionColumnHandler<R> actionColumnHandler) {

		this.actionColumnHandler = Optional.of(actionColumnHandler);
	}

	@Override
	public Optional<IEmfDataTableActionColumnHandler<R>> getActionColumnHandler() {

		return actionColumnHandler;
	}

	@Override
	public Optional<Consumer<Collection<R>>> getCallbackAfterContentChange() {

		return callbackAfterContentChange;
	}

	public void setCallbackAfterContentChange(Consumer<Collection<R>> callback) {

		this.callbackAfterContentChange = Optional.of(callback);
	}

	@Override
	public Optional<IDataTablePrefetcher<R>> getCallbackBeforePrefetch() {

		return callbackBeforePrefetch;
	}

	public void setCallbackBeforePrefetch(IDataTablePrefetcher<R> callbackBeforePrefetch) {

		this.callbackBeforePrefetch = Optional.of(callbackBeforePrefetch);
	}

	public <T> void setColumnHandler(IDataTableColumn<R, T> column, IEmfDataTableValueBasedColumnHandler<T> columnHandler) {

		columnHandlerMap.setColumnHandler(column, columnHandler);
	}

	public <T> void setColumnHandler(IDataTableColumn<R, T> column, IEmfDataTableRowBasedColumnHandler<R, T> columnHandler) {

		columnHandlerMap.setColumnHandler(column, columnHandler);
	}

	@Override
	public EmfDataTableColumnSettings getColumnSettings(IDataTableColumn<R, ?> column) {

		return columnSettingsMap.getSettings(column);
	}

	@Override
	public <T> IEmfDataTableRowBasedColumnHandler<R, T> getColumnHandler(IDataTableColumn<R, T> column) {

		return columnHandlerMap.getColumnHandler(column);
	}

	@Override
	public Supplier<IDbDatabaseScope> getDatabaseScopeSupplier() {

		return databaseScopeSupplier;
	}

	public void setDatabaseScopeSupplier(Supplier<IDbDatabaseScope> databaseScopeSupplier) {

		this.databaseScopeSupplier = databaseScopeSupplier;
	}

	public void setHideNavigation(boolean hideNavigation) {

		this.hideNavigationAtBottom = hideNavigation;
		this.hideNavigationAtTop = hideNavigation;
	}

	@Override
	public boolean isHideNavigationAtBottom() {

		return hideNavigationAtBottom;
	}

	public void setHideNavigationAtBottom(boolean hideNavigationAtBottom) {

		this.hideNavigationAtBottom = hideNavigationAtBottom;
	}

	@Override
	public boolean isHideNavigationAtTop() {

		return hideNavigationAtTop;
	}

	public void setHideNavigationAtTop(boolean hideNavigationAtTop) {

		this.hideNavigationAtTop = hideNavigationAtTop;
	}

	@Override
	public EmfDataTableOrdering<R> getOrdering() {

		return ordering;
	}

	public void clearOrderBy() {

		ordering.clear();
		dataTable.getSorters().clear();
	}

	public void setOrderBy(IDataTableColumn<R, ?> column, OrderDirection direction) {

		clearOrderBy();
		addOrderBy(column, direction);
	}

	public void addOrderBy(IDataTableColumn<R, ?> column, OrderDirection direction) {

		Objects.requireNonNull(column);
		Objects.requireNonNull(direction);

		ordering.add(column, direction);
		dataTable.getSorters().addSorter(column, direction);
	}

	@Override
	public IStaticObject getTableMarker() {

		return tableMarker;
	}

	public void setTableMarker(IStaticObject marker) {

		this.tableMarker = marker;
	}

	@Override
	public IStaticObject getTableDivMarker() {

		return tableDivMarker;
	}

	public void setTableDivMarker(IStaticObject tableDivMarker) {

		this.tableDivMarker = tableDivMarker;
	}

	@Override
	public int getPageSize() {

		return pageSize;
	}

	public void setPageSize(int pageSize) {

		this.pageSize = pageSize;
	}

	@Override
	public boolean isRowSelectionEnabled() {

		return rowSelectionMode != null && rowSelectionMode != EmfDataTableRowSelectionMode.NONE;
	}

	@Override
	public EmfDataTableRowSelectionMode getRowSelectionMode() {

		return rowSelectionMode;
	}

	public void setRowSelectionMode(EmfDataTableRowSelectionMode rowSelectionMode) {

		this.rowSelectionMode = Objects.requireNonNull(rowSelectionMode);
	}

	@Override
	public IEmfDataTableRowSelectionCallback<R> getRowSelectionCallback() {

		return rowSelectionCallback;
	}

	public void setRowSelectionCallback(IEmfDataTableRowSelectionCallback<R> rowSelectionCallback) {

		this.rowSelectionCallback = rowSelectionCallback;
	}

	@Override
	public List<DomButton> getRowSelectionActionButtons() {

		return rowSelectionActionButtons;
	}

	public void addRowSelectionActionButton(DomButton button) {

		this.rowSelectionActionButtons.add(button);
	}

	public void setRowCustomizer(IEmfDataTableRowCustomizer<R> rowCustomizer) {

		this.rowCustomizer = Optional.ofNullable(rowCustomizer);
	}

	@Override
	public Optional<IEmfDataTableRowCustomizer<R>> getRowCustomizer() {

		return rowCustomizer;
	}

	@Override
	public IEmfDataTableExtraRowColumnGroupListAccumulator<R> getSecondaryHeaderRowAccumulator() {

		return secondaryHeaderRowAccumulator;
	}

	@Override
	public IEmfDataTableExtraRowColumnGroupListAccumulator<R> getFooterRowAccumulator() {

		return footerRowAccumulator;
	}

	private class NoOperationDatabaseScope implements IDbDatabaseScope {

		@Override
		public void close() {

			// nothing
		}
	}
}
