package com.softicar.platform.emf.data.table.header.secondary;

import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.IEmfDataTableRowProvider;
import java.util.Objects;

/**
 * An additional header or footer row.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmfDataTableExtraRow<R> extends DomRow {

	private final IEmfDataTable<R> dataTable;
	private final IEmfDataTableRowProvider<R> rowProvider;
	private final IEmfDataTableExtraRowColumnGroupList<R> columnGroupList;

	public EmfDataTableExtraRow(IEmfDataTable<R> dataTable, IEmfDataTableRowProvider<R> rowProvider, IEmfDataTableExtraRowColumnGroupList<R> columnGroupList) {

		this.dataTable = Objects.requireNonNull(dataTable);
		this.rowProvider = Objects.requireNonNull(rowProvider);
		this.columnGroupList = Objects.requireNonNull(columnGroupList);
		setMarker(EmfDataTableDivMarker.EXTRA_ROW);
	}

	public void refresh() {

		removeChildren();

		new EmfDataTableExtraRowCellsComputer<>(dataTable, columnGroupList)//
			.getColumnSpans()
			.stream()
			.map(span -> new EmfDataTableExtraRowCell<>(span, rowProvider))
			.forEach(this::appendChild);
	}
}
