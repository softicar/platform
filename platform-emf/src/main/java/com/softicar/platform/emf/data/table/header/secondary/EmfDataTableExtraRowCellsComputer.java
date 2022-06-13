package com.softicar.platform.emf.data.table.header.secondary;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class EmfDataTableExtraRowCellsComputer<R> {

	private final Map<IDataTableColumn<?, ?>, IEmfDataTableExtraRowColumnGroup<R>> columnToGroupMap;
	private final List<EmfDataTableExtraRowColumnSpan<R>> columnSpans;
	private EmfDataTableExtraRowColumnSpan<R> currentSpan;

	public EmfDataTableExtraRowCellsComputer(IEmfDataTable<?> table, IEmfDataTableExtraRowColumnGroupList<R> columnGroupList) {

		this.columnToGroupMap = createColumToGroupMap(columnGroupList);
		this.columnSpans = new ArrayList<>();

		// action column
		if (table.getConfig().isRowSelectionEnabled() || table.getConfig().getActionColumnHandler().isPresent()) {
			addColumn();
		}

		// data columns
		for (IEmfDataTableColumn<?, ?> column: table.getColumnsInCustomOrder()) {
			if (!column.isConcealed() && !column.isHidden()) {
				addColumn(Optional.ofNullable(columnToGroupMap.get(column.getDataColumn())));
			}
		}
	}

	public List<EmfDataTableExtraRowColumnSpan<R>> getColumnSpans() {

		return columnSpans;
	}

	private void addColumn() {

		addColumn(Optional.empty());
	}

	private void addColumn(Optional<IEmfDataTableExtraRowColumnGroup<R>> columnGroup) {

		if (currentSpan != null && currentSpan.isColumnGroup(columnGroup)) {
			currentSpan.incrementColumnCount();
		} else {
			currentSpan = new EmfDataTableExtraRowColumnSpan<>(columnGroup);
			columnSpans.add(currentSpan);
		}
	}

	private Map<IDataTableColumn<?, ?>, IEmfDataTableExtraRowColumnGroup<R>> createColumToGroupMap(IEmfDataTableExtraRowColumnGroupList<R> columnGroupList) {

		Map<IDataTableColumn<?, ?>, IEmfDataTableExtraRowColumnGroup<R>> columnToGroupMap = new IdentityHashMap<>();
		for (IEmfDataTableExtraRowColumnGroup<R> columnGroup: columnGroupList.getColumnGroups()) {
			for (IDataTableColumn<?, ?> column: columnGroup.getColumns()) {
				columnToGroupMap.put(column, columnGroup);
			}
		}
		return columnToGroupMap;
	}
}
