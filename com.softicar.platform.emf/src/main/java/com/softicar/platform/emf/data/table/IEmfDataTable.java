package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import java.util.Collection;

/**
 * This the interface of {@link EmfDataTable}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public interface IEmfDataTable<R> extends IDomElement, IRefreshable {

	IDataTable<R> getDataTable();

	IEmfDataTableConfig<R> getConfig();

	IEmfDataTableController<R> getController();

	Collection<IEmfDataTableColumn<R, ?>> getColumnsOrderedByCustomIndex();

	Collection<IEmfDataTableRow<R>> getDisplayedTableRows();

	void prefetchData(Collection<R> dataRows);

	// FIXME two boolean parameters is bad design; improve API
	void setRowSelected(IEmfDataTableRow<R> tableRow, boolean doSelect, boolean doCallbacks);
}
