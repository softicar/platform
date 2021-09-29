package com.softicar.platform.emf.data.table.filter.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelectBuilder;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import java.util.Collection;

class EmfDataTableEntityValueSelectBuilder<T extends IEntity> extends DomSimpleValueSelectBuilder<T> {

	private static final int MAX_ITEMS_IN_DROPDOWN = 5000;

	public EmfDataTableEntityValueSelectBuilder(IEmfDataTableColumn<?, T> column) {

		setValues(getColumnValues(column));
	}

	private static <T> Collection<T> getColumnValues(IEmfDataTableColumn<?, T> column) {

		Collection<T> columnValues = column.getDistinctColumnValues(MAX_ITEMS_IN_DROPDOWN);
		if (columnValues.size() >= MAX_ITEMS_IN_DROPDOWN) {
			// FIXME
//			LogDb.panic("Max items in filter exceeded: see ticket #11401");
		}
		column.getDataColumn().prefetchData(columnValues);
		return columnValues;
	}
}
