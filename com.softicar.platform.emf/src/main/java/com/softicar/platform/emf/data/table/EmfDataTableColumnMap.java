package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.map.index.IIndexMap;
import com.softicar.platform.common.container.map.index.IndexHashMap;
import com.softicar.platform.emf.data.table.column.EmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map between {@link IDataTableColumn} and {@link IEmfDataTableColumn}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
class EmfDataTableColumnMap<R> {

	private final Map<IDataTableColumn<R, ?>, IEmfDataTableColumn<R, ?>> columnMap;
	private IIndexMap<IDataTableColumn<R, ?>> customIndexMap;

	public EmfDataTableColumnMap(IEmfDataTableController<R> controller, IDataTable<R> dataTable) {

		this.customIndexMap = new IndexHashMap<>(dataTable.getTableColumns());
		this.columnMap = new HashMap<>();

		for (IDataTableColumn<R, ?> column: dataTable.getTableColumns()) {
			this.columnMap.put(column, new EmfDataTableColumn<>(controller, column));
		}
	}

	public Set<IDataTableColumn<R, ?>> getDataColumns() {

		return columnMap.keySet();
	}

	public IEmfDataTableColumn<R, ?> getEmfColumn(IDataTableColumn<R, ?> dataColumn) {

		return columnMap.get(dataColumn);
	}

	public IIndexMap<IDataTableColumn<R, ?>> getCustomIndexMap() {

		return customIndexMap;
	}

	public void setCustomIndexMap(IIndexMap<IDataTableColumn<R, ?>> customIndexMap) {

		this.customIndexMap = customIndexMap;
	}
}
