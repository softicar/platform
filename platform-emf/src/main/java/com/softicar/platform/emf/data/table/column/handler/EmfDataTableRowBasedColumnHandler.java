package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.filtering.IEmfDataTableColumnFilterNodeFactory;
import com.softicar.platform.emf.data.table.column.handler.filtering.IEmfDataTableRowBasedColumnFilterNodeFactory;
import java.util.Collection;

/**
 * Default implementation of {@link IEmfDataTableRowBasedColumnHandler}.
 * <p>
 * This is the recommended base class for row-based column handlers, supporting
 * filtering and sorting. As fall-back implementation, a value-based column
 * handler is used internally.
 *
 * @author Oliver Richers
 */
public class EmfDataTableRowBasedColumnHandler<R, T> implements IEmfDataTableRowBasedColumnHandler<R, T> {

	private final IEmfDataTableValueBasedColumnHandler<T> valueBasedColumnHandler;

	/**
	 * Creates a new row-based column handler, based on
	 * {@link EmfDataTableValueBasedColumnHandler}.
	 */
	public EmfDataTableRowBasedColumnHandler() {

		this(new EmfDataTableValueBasedColumnHandler<>());
	}

	/**
	 * Creates a new row-based column handler, based on the given value-based
	 * column handler.
	 *
	 * @param valueBasedColumnHandler
	 *            the fall-back column handler to use
	 */
	public EmfDataTableRowBasedColumnHandler(IEmfDataTableValueBasedColumnHandler<T> valueBasedColumnHandler) {

		this.valueBasedColumnHandler = valueBasedColumnHandler;
	}

	@Override
	public void prefetchData(IEmfDataTableColumn<R, T> column, Collection<R> rows) {

		valueBasedColumnHandler.prefetchData(column, column.getDataColumn().getValues(rows));
	}

	@Override
	public void buildCell(IEmfDataTableCell<R, T> cell, R row) {

		valueBasedColumnHandler.buildCell(cell, cell.getColumn().getDataColumn().getValue(row));
	}

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, T> column) {

		return valueBasedColumnHandler.isSortable(column);
	}

	@Override
	public boolean isReverseOrderDirection() {

		return valueBasedColumnHandler.isReverseOrderDirection();
	}

	@Override
	public IEmfDataTableRowBasedColumnFilterNodeFactory<R> getFilterNodeFactory(IEmfDataTableColumn<R, T> column) {

		IEmfDataTableColumnFilterNodeFactory<T> factory = valueBasedColumnHandler.getFilterNodeFactory(column);
		if (factory != null) {
			return () -> factory.createFilterNode(column);
		} else {
			return null;
		}
	}
}
