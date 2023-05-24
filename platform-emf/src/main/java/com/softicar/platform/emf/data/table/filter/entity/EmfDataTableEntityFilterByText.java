package com.softicar.platform.emf.data.table.filter.entity;

import com.softicar.platform.common.container.data.table.DataTableCollectionFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import java.util.Collection;
import java.util.stream.Collectors;

class EmfDataTableEntityFilterByText<R, T extends IEntity> implements IEmfDataTableFilter<R> {

	private final IEmfDataTableColumn<R, T> column;
	private final DataTableCollectionFilterOperator operator;
	private final String filterText;
	private final INullaryVoidFunction resetter;
	private final Collection<T> entities;

	public EmfDataTableEntityFilterByText(IEmfDataTableColumn<R, T> column, DataTableCollectionFilterOperator operator, String filterText,
			INullaryVoidFunction resetter) {

		this.column = column;
		this.operator = operator;
		this.filterText = filterText;
		this.resetter = resetter;
		this.entities = getFilteredEntities();
	}

	@Override
	public IDisplayString getDisplayString() {

		return switch (operator) {
		case IN -> EmfDataTableI18n.CONTAINS.concat(" '").concat(filterText).concat("'");
		case NOT_IN -> EmfDataTableI18n.DOES_NOT_CONTAIN.concat(" '").concat(filterText).concat("'");
		};
	}

	@Override
	public void addTo(IDataTableFilterList<R> filters) {

		filters.addCollectionFilter(column.getDataColumn(), operator, entities);
	}

	@Override
	public void reset() {

		resetter.apply();
	}

	private Collection<T> getFilteredEntities() {

		var entities = column.getDistinctColumnValues();
		column.getDataColumn().prefetchData(entities);
		return entities//
			.stream()
			.filter(this::matchesFilterText)
			.collect(Collectors.toList());
	}

	private boolean matchesFilterText(T entity) {

		return EmfDataTableEntityFilterNode.toDisplayStringSafely(entity).toString().toLowerCase().contains(filterText);
	}
}
