package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.data.table.column.settings.IEmfDataTableColumnSettings;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import java.util.Collection;
import java.util.List;

/**
 * This the user interface for {@link IDataTable}.
 * <p>
 * Use {@link EmfDataTableDivBuilder} to create new instances.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public interface IEmfDataTableDiv<R> extends IDomElement, IRefreshable {

	// -------------------- rows -------------------- //

	int getTotalRowCount();

	List<IEmfDataTableRow<R>> getDisplayedTableRows();

	// -------------------- row selection -------------------- //

	boolean isRowSelectionEnabled();

	EmfDataTableRowSelectionMode getRowSelectionMode();

	Collection<R> getSelectedRows();

	void unselectAllRowsOfTable();

	// -------------------- column properties -------------------- //

	IEmfDataTableColumnSettings getColumnSettings(IDataTableColumn<R, ?> column);

	boolean isSortable(IDataTableColumn<R, ?> column);

	boolean isFilterable(IDataTableColumn<R, ?> column);

	// -------------------- paging -------------------- //

	int getCurrentPage();

	void setCurrentPage(int pageIndex);

	void showLastPage();

	int getPageCount();

	int getPageSize();

	// -------------------- export -------------------- //

	void export(ITableExportEngine<?> engine);
}
