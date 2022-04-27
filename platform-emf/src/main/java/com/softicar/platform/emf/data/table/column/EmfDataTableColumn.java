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

public class EmfDataTableColumn<R, V> implements IEmfDataTableColumn<R, V> {

	private final IEmfDataTableController<R> controller;
	private final IDataTableColumn<R, V> dataColumn;
	private EmfDataTableFilterPopup<R> filterPopup;

	public EmfDataTableColumn(IEmfDataTableController<R> controller, IDataTableColumn<R, V> dataColumn) {

		this.controller = controller;
		this.dataColumn = dataColumn;
		this.filterPopup = null;
	}

	@Override
	public IEmfDataTableController<R> getController() {

		return controller;
	}

	@Override
	public IDataTableColumn<R, V> getDataColumn() {

		return dataColumn;
	}

	@Override
	public IEmfDataTableColumnSettings getSettings() {

		return controller.getColumnSettings(dataColumn);
	}

	@Override
	public IEmfDataTableRowBasedColumnHandler<R, V> getColumnHandler() {

		return controller.getColumnHandler(dataColumn);
	}

	@Override
	public Collection<V> getDistinctColumnValues() {

		return controller.getDataTable().getDistinctColumnValues(dataColumn);
	}

	@Override
	public Collection<V> getDistinctColumnValues(int limit) {

		return controller.getDataTable().getDistinctColumnValues(dataColumn, limit);
	}

	@Override
	public boolean isConcealed() {

		return getSettings().isConcealed();
	}

	@Override
	public IDisplayString getTitle() {

		IDisplayString title = getSettings().getTitleOverride();
		if (title == null) {
			title = getDataColumn().getTitle();
		}
		return title;
	}

	// -------------------- hiding -------------------- //

	@Override
	public void setHidden(boolean hidden) {

		controller.setHidden(this, hidden);
	}

	@Override
	public boolean isHidden() {

		return getSettings().isHidden();
	}

	// -------------------- sorting -------------------- //

	@Override
	public boolean isSortable() {

		return getColumnHandler().isSortable(this);
	}

	@Override
	public void setOrderByThisColumn(OrderDirection direction) {

		controller.setOrderByColumn(dataColumn, direction);
	}

	@Override
	public void addOrderByThisColumn(OrderDirection direction) {

		controller.addOrderByColumn(dataColumn, direction);
	}

	@Override
	public void removeOrderByThisColumn() {

		controller.removeOrderByColumn(dataColumn);
	}

	@Override
	public Optional<Integer> getOrderByIndex() {

		return controller.getOrdering().getColumnIndex(dataColumn);
	}

	@Override
	public Optional<OrderDirection> getOrderByDirection() {

		return controller.getOrdering().getOrderDirection(dataColumn);
	}

	// -------------------- filtering -------------------- //

	@Override
	public boolean isFilterable() {

		return getColumnHandler().getFilterNodeFactory(this) != null;
	}

	@Override
	public EmfDataTableFilterPopup<R> getFilterPopup() {

		if (filterPopup == null) {
			this.filterPopup = new EmfDataTableFilterPopup<>(this);
		}
		return filterPopup;
	}

	@Override
	public void resetFilterPopup() {

		if (filterPopup != null) {
			filterPopup.close();
		}
		this.filterPopup = null;
	}

	@Override
	public void setColumnFilter(IEmfDataTableFilter<R> filter) {

		controller.setColumnFilter(dataColumn, filter);
	}

	@Override
	public IEmfDataTableFilter<R> getColumnFilter() {

		return controller.getColumnFilter(dataColumn);
	}

	@Override
	public void removeColumnFilter() {

		controller.removeColumnFilter(dataColumn);
	}
}
