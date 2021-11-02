package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.utils.CastUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the mapping between {@link IDataTableColumn} and
 * {@link IEmfDataTableRowBasedColumnHandler}.
 *
 * @author Oliver Richers
 */
public class EmfDataTableColumnHandlerMap<R> {

	private final Map<IDataTableColumn<R, ?>, IEmfDataTableRowBasedColumnHandler<R, ?>> columnHandlers;

	public EmfDataTableColumnHandlerMap() {

		this.columnHandlers = new HashMap<>();
	}

	public <T> void setColumnHandler(IDataTableColumn<R, T> column, IEmfDataTableRowBasedColumnHandler<R, T> columnHandler) {

		columnHandlers.put(column, columnHandler);
	}

	public <T> void setColumnHandler(IDataTableColumn<R, T> column, IEmfDataTableValueBasedColumnHandler<T> columnHandler) {

		setColumnHandler(column, new EmfDataTableRowBasedColumnHandler<R, T>(columnHandler));
	}

	public <T> IEmfDataTableRowBasedColumnHandler<R, T> getColumnHandler(IDataTableColumn<R, T> column) {

		IEmfDataTableRowBasedColumnHandler<R, T> columnHandler = CastUtils.cast(columnHandlers.get(column));
		if (columnHandler == null) {
			columnHandler = new EmfDataTableRowBasedColumnHandler<>();
			this.columnHandlers.put(column, columnHandler);
		}
		return columnHandler;
	}
}
