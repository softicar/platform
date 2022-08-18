package com.softicar.platform.emf.data.table.header.secondary;

import com.softicar.platform.dom.elements.DomHeaderCell;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.IEmfDataTableRowProvider;
import java.util.Objects;

/**
 * The table cell of an additional header or footer row.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
class EmfDataTableExtraRowCell<R> extends DomHeaderCell {

	public EmfDataTableExtraRowCell(EmfDataTableExtraRowColumnSpan<R> columnSpan, IEmfDataTableRowProvider<R> rowProvider) {

		Objects.requireNonNull(columnSpan);
		Objects.requireNonNull(rowProvider);

		addMarker(EmfTestMarker.DATA_TABLE_EXTRA_ROW_CELL);
		setColSpan(columnSpan.getColumnCount());
		columnSpan.getColumnGroup().ifPresent(columnGroup -> {
			columnGroup.getCellBuilder().buildCell(this, rowProvider);
		});
	}
}
