package com.softicar.platform.emf.data.table.filter.entity;

import com.softicar.platform.common.container.data.table.DataTableCollectionFilterOperator;
import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableEmptyValueFilter;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableNotEmptyValueFilter;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableValueFilter;

class EmfDataTableEntityFilterFactory<R, T extends IEntity> {

	private final EmfDataTableEntityFilterNode<R, T> filterNode;
	private final EmfDataTableEntityFilterState<T> filterState;
	private final IEmfDataTableColumn<R, T> column;

	public EmfDataTableEntityFilterFactory(EmfDataTableEntityFilterNode<R, T> filterNode) {

		this.filterNode = filterNode;
		this.filterState = filterNode.getFilterState();
		this.column = filterNode.getColumn();
	}

	public IEmfDataTableFilter<R> createFilter() {

		return switch (filterState.getFilterType()) {
		case CONTAINS_TEXT -> createTextFilter(DataTableCollectionFilterOperator.IN);
		case DOES_NOT_CONTAIN_TEXT -> createTextFilter(DataTableCollectionFilterOperator.NOT_IN);
		case IS -> createEntityFilter(DataTableValueFilterOperator.EQUAL);
		case IS_NOT -> createEntityFilter(DataTableValueFilterOperator.NOT_EQUAL);
		case IS_EMPTY -> new EmfDataTableEmptyValueFilter<>(column.getDataColumn(), this::resetState);
		case IS_NOT_EMPTY -> new EmfDataTableNotEmptyValueFilter<>(column.getDataColumn(), this::resetState);
		};
	}

	private IEmfDataTableFilter<R> createTextFilter(DataTableCollectionFilterOperator operator) {

		var filterText = filterState.getFilterText().trim().toLowerCase();
		return filterText.isBlank()? createNopFilter() : new EmfDataTableEntityFilterByText<>(column, operator, filterText, this::resetState);
	}

	private IEmfDataTableFilter<R> createEntityFilter(DataTableValueFilterOperator operator) {

		return filterState//
			.getFilterEntity()
			.map(filterValue -> createEntityFilter(operator, filterValue))
			.orElseGet(this::createNopFilter);
	}

	private IEmfDataTableFilter<R> createEntityFilter(DataTableValueFilterOperator operator, T filterValue) {

		return new EmfDataTableValueFilter<>(column, operator, filterValue, this::resetState);
	}

	private EmfDataTableNopFilter<R> createNopFilter() {

		return new EmfDataTableNopFilter<>(this::resetState);
	}

	private void resetState() {

		filterNode.applyFilterState(filterState);
	}
}