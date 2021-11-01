package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionColumnHandler;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.filtering.IEmfDataTableColumnFilterNodeFactory;
import java.util.Collection;

/**
 * Recommended handler base class for un-filterable and un-sortable columns.
 * <p>
 * Do not use this class to implement an action column (anymore). Rather
 * implement the interface {@link IEmfDataTableActionColumnHandler} and call the
 * method {@link EmfDataTableDivBuilder#setActionColumnHandler}.
 * <p>
 * For column handlers that want to provide sorting and filtering, derive from
 * {@link EmfDataTableValueBasedColumnHandler}.
 *
 * @see EmfDataTableValueBasedColumnHandler
 * @author Oliver Richers
 */
public abstract class AbstractEmfDataTableColumnHandler<T> implements IEmfDataTableValueBasedColumnHandler<T> {

	@Override
	public void prefetchData(IEmfDataTableColumn<?, T> column, Collection<T> values) {

		column.getDataColumn().prefetchData(values);
	}

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, T> column) {

		return false;
	}

	@Override
	public boolean isReverseOrderDirection() {

		return false;
	}

	@Override
	public IEmfDataTableColumnFilterNodeFactory<T> getFilterNodeFactory(IEmfDataTableColumn<?, T> column) {

		return null;
	}
}
