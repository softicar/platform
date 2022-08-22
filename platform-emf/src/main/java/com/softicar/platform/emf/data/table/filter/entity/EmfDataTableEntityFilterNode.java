package com.softicar.platform.emf.data.table.filter.entity;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.AbstractEmfDataTableMultiTypeFilterDiv;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;
import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableEmptyValueFilter;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableNotEmptyValueFilter;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableValueFilter;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmfDataTableEntityFilterNode<R, T extends IEntity> extends AbstractEmfDataTableMultiTypeFilterDiv<R, EmfDataTableEntityFilterType> {

	private final IEmfDataTableColumn<R, T> column;
	private final EmfDataTableEntityFilterTypeSelect filterTypeSelect;
	private final IDomAutoCompleteInput<T> entityInput;

	public EmfDataTableEntityFilterNode(IEmfDataTableColumn<R, T> column) {

		this.column = column;
		this.filterTypeSelect = new EmfDataTableEntityFilterTypeSelect(this);
		this.entityInput = new EntityInput<>(column);
		this.entityInput.addMarker(EmfTestMarker.DATA_TABLE_FILTER_INPUT_ENTITY);
		refresh();
	}

	@Override
	public IEmfDataTableFilter<R> createFilter() {

		Optional<T> filterValue = entityInput.getValue();
		EmfDataTableEntityFilterType filterType = filterTypeSelect.getSelectedValue();
		Resetter resetter = new Resetter(filterType, filterValue.orElse(null));
		if (filterValue.isPresent()) {
			switch (filterType) {
			case IS:
				return new EmfDataTableValueFilter<>(column, DataTableValueFilterOperator.EQUAL, filterValue.get(), resetter);
			case IS_NOT:
				return new EmfDataTableValueFilter<>(column, DataTableValueFilterOperator.NOT_EQUAL, filterValue.get(), resetter);
			case IS_EMPTY:
				throw new UnsupportedOperationException();
			case IS_NOT_EMPTY:
				throw new UnsupportedOperationException();
			}
			throw new SofticarUnknownEnumConstantException(filterType);
		} else {
			if (filterType.isEmpty()) {
				return new EmfDataTableEmptyValueFilter<>(column.getDataColumn(), resetter);
			} else if (filterType.isNotEmpty()) {
				return new EmfDataTableNotEmptyValueFilter<>(column.getDataColumn(), resetter);
			}
			return new EmfDataTableNopFilter<>(resetter);
		}
	}

	@Override
	public void refresh() {

		super.refresh();
		if (filterTypeSelect.getSelectedValue().isEmpty() || filterTypeSelect.getSelectedValue().isNotEmpty()) {
			entityInput.setDisplayNone(true);
			entityInput.setValue(null);
		} else {
			entityInput.setDisplayNone(false);
		}
	}

	@Override
	public IEmfDataTableFilterTypeSelect<EmfDataTableEntityFilterType> getFilterSelect() {

		return filterTypeSelect;
	}

	@Override
	public IDomNode getFilterInput(EmfDataTableEntityFilterType filterType) {

		return entityInput;
	}

	private static class EntityInput<T extends IEntity> extends DomAutoCompleteInput<T> {

		public EntityInput(IEmfDataTableColumn<?, T> column) {

			super(() -> getSortedColumnValues(column));
		}

		private static <T extends IEntity> Collection<T> getSortedColumnValues(IEmfDataTableColumn<?, T> column) {

			Collection<T> columnValues = column.getDistinctColumnValues();
			column.getDataColumn().prefetchData(columnValues);
			return columnValues//
				.stream()
				.sorted(Comparator.comparing(entity -> toDisplayStringSafely(entity).toString()))
				.collect(Collectors.toList());
		}

		private static IDisplayString toDisplayStringSafely(IEntity entity) {

			return Optional//
				.ofNullable(entity)
				.map(IEntity::toDisplay)
				.orElse(IDisplayString.EMPTY);
		}
	}

	private class Resetter implements INullaryVoidFunction {

		private final EmfDataTableEntityFilterType filterType;
		private final T filterValue;

		public Resetter(EmfDataTableEntityFilterType filterType, T filterValue) {

			this.filterType = filterType;
			this.filterValue = filterValue;
		}

		@Override
		public void apply() {

			filterTypeSelect.setSelectedValue(filterType);
			entityInput.setValue(filterValue);
		}
	}
}
