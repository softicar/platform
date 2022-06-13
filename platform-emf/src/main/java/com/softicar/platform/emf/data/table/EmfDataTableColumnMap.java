package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.map.index.IIndexMap;
import com.softicar.platform.common.container.map.index.IndexHashMap;
import com.softicar.platform.emf.data.table.column.EmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Map between {@link IDataTableColumn} and {@link IEmfDataTableColumn}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
class EmfDataTableColumnMap<R> {

	private final List<IDataTableColumn<R, ?>> columnsInDefaultOrder;
	private final Map<IDataTableColumn<R, ?>, IEmfDataTableColumn<R, ?>> columnMap;
	private IIndexMap<IDataTableColumn<R, ?>> customIndexMap;

	public EmfDataTableColumnMap(IEmfDataTableController<R> controller, IEmfDataTableConfig<R> config) {

		this.columnsInDefaultOrder = config//
			.getDataTable()
			.getTableColumns()
			.stream()
			.filter(column -> !config.getColumnSettings(column).isConcealed())
			.collect(Collectors.toList());
		this.customIndexMap = new IndexHashMap<>(columnsInDefaultOrder);
		this.columnMap = new HashMap<>();

		for (IDataTableColumn<R, ?> column: columnsInDefaultOrder) {
			this.columnMap.put(column, new EmfDataTableColumn<>(controller, column));
		}
	}

	public List<IDataTableColumn<R, ?>> getDataColumnsInDefaultOrder() {

		return columnsInDefaultOrder;
	}

	public List<IEmfDataTableColumn<R, ?>> getEmfColumnsInDefaultOrder() {

		return columnsInDefaultOrder//
			.stream()
			.map(this::getEmfColumn)
			.collect(Collectors.toList());
	}

	public List<IEmfDataTableColumn<R, ?>> getEmfColumnsInCustomOrder() {

		return columnsInDefaultOrder//
			.stream()
			.sorted(customIndexMap)
			.map(this::getEmfColumn)
			.collect(Collectors.toList());
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
