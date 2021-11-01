package com.softicar.platform.emf.attribute.data.table;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.Optional;

public class EmfAttributeColumnHandler<R extends IEmfTableRow<R, ?>, V> extends EmfDataTableRowBasedColumnHandler<R, V> {

	private final IEmfAttribute<R, V> attribute;
	private final boolean sortable;

	public EmfAttributeColumnHandler(IEmfAttribute<R, V> attribute) {

		this.attribute = attribute;
		this.sortable = attribute.getValueComparator().isPresent();
	}

	@Override
	public void prefetchData(IEmfDataTableColumn<R, V> column, Collection<R> entities) {

		attribute.prefetchValues(entities);
	}

	@Override
	public void buildCell(IEmfDataTableCell<R, V> cell, R tableRow) {

		Optional<IDomElement> display = attribute.createTabularDisplay(tableRow);
		if (display.isPresent()) {
			cell.appendChild(display.get());
		} else {
			super.buildCell(cell, tableRow);
		}
	}

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, V> column) {

		return sortable;
	}
}
