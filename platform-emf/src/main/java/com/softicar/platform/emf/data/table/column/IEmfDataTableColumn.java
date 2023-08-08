package com.softicar.platform.emf.data.table.column;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.data.table.IEmfDataTableController;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.data.table.column.settings.IEmfDataTableColumnSettings;
import com.softicar.platform.emf.data.table.filter.EmfDataTableFilterPopup;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import java.util.Collection;
import java.util.Optional;

public interface IEmfDataTableColumn<R, T> {

	IEmfDataTableController<R> getController();

	IDataTableColumn<R, T> getDataColumn();

	IEmfDataTableColumnSettings getSettings();

	IEmfDataTableRowBasedColumnHandler<R, T> getColumnHandler();

	Collection<T> getDistinctColumnValues();

	Collection<T> getDistinctColumnValues(int limit);

	boolean isConcealed();

	IDisplayString getTitle();

	// -------------------- hiding -------------------- //

	boolean isHidden();

	// -------------------- ordering -------------------- //

	boolean isSortable();

	void addOrderByThisColumn(OrderDirection direction);

	void setOrderByThisColumn(OrderDirection direction);

	void removeOrderByThisColumn();

	Optional<Integer> getOrderByIndex();

	Optional<OrderDirection> getOrderByDirection();

	// -------------------- filtering -------------------- //

	boolean isFilterable();

	EmfDataTableFilterPopup<R> getFilterPopup();

	void resetFilterPopup();

	void setColumnFilter(IEmfDataTableFilter<R> filter);

	IEmfDataTableFilter<R> getColumnFilter();

	void removeColumnFilter();
}
