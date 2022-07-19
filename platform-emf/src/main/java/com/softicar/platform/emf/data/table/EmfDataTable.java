package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.db.core.database.IDbDatabaseScope;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.DomTFoot;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTableConfiguration;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.empty.EmfDataTableEmptyTablePlaceholderRow;
import com.softicar.platform.emf.data.table.export.ITableExportDomTableExtension;
import com.softicar.platform.emf.data.table.header.secondary.EmfDataTableExtraRow;
import com.softicar.platform.emf.data.table.header.secondary.IEmfDataTableExtraRowColumnGroupList;
import com.softicar.platform.emf.data.table.header.secondary.IEmfDataTableExtraRowColumnGroupListAccumulator;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * A {@link DomTable} showing the content of an {@link IDataTable}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
class EmfDataTable<R> extends DomPageableTable implements IEmfDataTable<R>, ITableExportDomTableExtension {

	private final IEmfDataTableConfig<R> config;
	private final IEmfDataTableController<R> controller;
	private final EmfDataTableLazyRowCache<R> lazyRowCache;
	private final List<EmfDataTableExtraRow<R>> secondaryHeaderRows;
	private final EmfDataTableHeaderRow<R> primaryHeaderRow;
	private final List<EmfDataTableExtraRow<R>> footerRows;

	/**
	 * @param config
	 * @param rowSelectionControlElementSupplier
	 *            TODO: this is ugly. pass the div instead and expose the
	 *            control element?
	 */
	protected EmfDataTable(IEmfDataTableConfig<R> config, Supplier<IRefreshable> rowSelectionControlElementSupplier) {

		super(new DomPageableTableConfiguration().setPageSize(config.getPageSize()));

		this.config = config;
		this.controller = new EmfDataTableController<>(this, config, rowSelectionControlElementSupplier);
		this.lazyRowCache = new EmfDataTableLazyRowCache<>(this::loadAllRows);
		this.secondaryHeaderRows = createSecondaryHeaderRows(config);
		this.primaryHeaderRow = new EmfDataTableHeaderRow<>(this);
		this.footerRows = createFooterRows(config);

		setPostPagingHook(controller);

		appendHeaderRows();
		refreshHeaderRows();

		refreshBody();

		appendFooterRows();
		refreshFooterRows();

		addCssClass(EmfCssClasses.EMF_DATA_TABLE);

		addDeferredInitializer(controller::restorePersistentTableConfiguration);

		refresh();
	}

	@Override
	public void refresh() {

		controller.getDataTable().invalidateCaches();
		if (config.isRowSelectionEnabled()) {
			controller.unselectAllRowsOfTable();
		}
		refreshHeaderRows();
		refreshBody();
		refreshFooterRows();
	}

	@Override
	public IDataTable<R> getDataTable() {

		return config.getDataTable();
	}

	@Override
	public IEmfDataTableConfig<R> getConfig() {

		return config;
	}

	@Override
	public IEmfDataTableController<R> getController() {

		return controller;
	}

	@Override
	public Collection<IEmfDataTableColumn<R, ?>> getColumnsInCustomOrder() {

		return controller.getColumnsInCustomOrder();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IEmfDataTableRow<R>> getDisplayedTableRows() {

		return super.getDisplayedRows()//
			.stream()
			.filter(row -> row instanceof IEmfDataTableRow)
			.map(row -> (IEmfDataTableRow<R>) row)
			.collect(Collectors.toList());
	}

	@Override
	public AutoCloseable startTransaction() {

		return new DbTransaction();
	}

	@Override
	public void prefetchData(Collection<R> dataRows) {

		try (IDbDatabaseScope scope = config.getDatabaseScopeSupplier().get()) {
			executePrefetch(dataRows);
		}
	}

	@Override
	public void setRowSelected(IEmfDataTableRow<R> tableRow, boolean doSelect, boolean doCallbacks) {

		controller.setRowSelected(tableRow, doSelect, doCallbacks);
	}

	@Override
	protected EmfDataTableRow<R> createRow(int index) {

		return createRows(index, index).iterator().next();
	}

	@Override
	protected Collection<EmfDataTableRow<R>> createRows(int first, int last) {

		return createDomRows(loadDataRowsAndPrefetchOtherData(first, last));
	}

	protected void invalidateRowCache() {

		lazyRowCache.invalidate();
	}

	private Collection<EmfDataTableRow<R>> createDomRows(List<R> dataRows) {

		try (IDbDatabaseScope scope = config.getDatabaseScopeSupplier().get()) {
			return dataRows//
				.stream()
				.map(this::createRow)
				.collect(Collectors.toList());
		}
	}

	private Collection<R> loadAllRows() {

		return loadDataRowsAndPrefetchOtherData(0, getTotalRowCount());
	}

	private List<R> loadDataRowsAndPrefetchOtherData(int first, int last) {

		List<R> dataRows = loadDataRows(first, last);
		prefetchData(dataRows);
		return dataRows;
	}

	private List<R> loadDataRows(int first, int last) {

		try (IDbDatabaseScope scope = config.getDatabaseScopeSupplier().get()) {
			return getDataTable().list(first, last - first + 1);
		}
	}

	private void appendHeaderRows() {

		secondaryHeaderRows.forEach(row -> getHead().appendChild(row));
		getHead().appendChild(primaryHeaderRow);
	}

	private void appendFooterRows() {

		if (!footerRows.isEmpty()) {
			DomTFoot footer = appendChild(new DomTFoot());
			footerRows.forEach(row -> footer.appendChild(row));
		}
	}

	private List<EmfDataTableExtraRow<R>> createSecondaryHeaderRows(IEmfDataTableConfig<R> config) {

		return createExtraRows(config.getSecondaryHeaderRowAccumulator());
	}

	private List<EmfDataTableExtraRow<R>> createFooterRows(IEmfDataTableConfig<R> config) {

		return createExtraRows(config.getFooterRowAccumulator());
	}

	private List<EmfDataTableExtraRow<R>> createExtraRows(IEmfDataTableExtraRowColumnGroupListAccumulator<R> accumulator) {

		return accumulator//
			.getAllGroupLists()
			.stream()
			.map(this::createExtraRow)
			.collect(Collectors.toList());
	}

	private EmfDataTableExtraRow<R> createExtraRow(IEmfDataTableExtraRowColumnGroupList<R> columnGroupList) {

		return new EmfDataTableExtraRow<>(this, lazyRowCache, columnGroupList);
	}

	private void refreshHeaderRows() {

		secondaryHeaderRows.stream().forEach(it -> it.refresh());
		primaryHeaderRow.refresh();
	}

	private void refreshFooterRows() {

		footerRows.stream().forEach(it -> it.refresh());
	}

	private void refreshBody() {

		int currentPage = getCurrentPage();
		int rowCount = loadRowCountFromCurrentDatabase();

		reset(rowCount, getPageSize());

		if (currentPage != 0) {
			setCurrentPage(currentPage);
		}

		if (rowCount <= 0) {
			getBody().appendChild(new EmfDataTableEmptyTablePlaceholderRow(config, this.primaryHeaderRow.getColumnCount()));
		}

		config//
			.getCallbackAfterContentChange()
			.ifPresent(callback -> callback.accept(lazyRowCache.getAllRows()));
	}

	private int loadRowCountFromCurrentDatabase() {

		try (IDbDatabaseScope scope = config.getDatabaseScopeSupplier().get()) {
			return loadRowCount();
		}
	}

	private int loadRowCount() {

		return getDataTable().count();
	}

	private void executePrefetch(Collection<R> dataRows) {

		// execute callback before prefetching
		config.getCallbackBeforePrefetch().ifPresent(it -> it.prefetchData(dataRows));

		// prefetch data for action column
		config.getActionColumnHandler().ifPresent(it -> it.prefetchData(dataRows));

		// prefetch data for non-concealed columns
		for (IEmfDataTableColumn<R, ?> tableColumn: controller.getColumnsInDefaultOrder()) {
			prefetchData(dataRows, tableColumn);
		}
	}

	private <T> void prefetchData(Collection<R> dataRows, IEmfDataTableColumn<R, T> column) {

		config.getColumnHandler(column.getDataColumn()).prefetchData(column, dataRows);
	}

	private EmfDataTableRow<R> createRow(R dataRow) {

		return new EmfDataTableRow<>(this, dataRow);
	}
}
