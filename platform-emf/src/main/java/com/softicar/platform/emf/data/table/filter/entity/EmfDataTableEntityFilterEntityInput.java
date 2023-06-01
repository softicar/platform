package com.softicar.platform.emf.data.table.filter.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

class EmfDataTableEntityFilterEntityInput<T extends IEntity> extends DomAutoCompleteInput<T> {

	public EmfDataTableEntityFilterEntityInput(IEmfDataTableColumn<?, T> column) {

		super(() -> getSortedColumnValues(column));
	}

	private static <T extends IEntity> Collection<T> getSortedColumnValues(IEmfDataTableColumn<?, T> column) {

		Collection<T> columnValues = column.getDistinctColumnValues();
		column.getDataColumn().prefetchData(columnValues);
		return columnValues//
			.stream()
			.sorted(Comparator.comparing(entity -> EmfDataTableEntityFilterNode.toDisplayStringSafely(entity).toString()))
			.collect(Collectors.toList());
	}
}