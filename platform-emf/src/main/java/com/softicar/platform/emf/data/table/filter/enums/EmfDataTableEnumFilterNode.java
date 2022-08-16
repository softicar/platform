package com.softicar.platform.emf.data.table.filter.enums;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomEnumSelect;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.AbstractEmfDataTableMultiTypeFilterDiv;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;
import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableValueFilter;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmfDataTableEnumFilterNode<R, T extends Enum<T>> extends AbstractEmfDataTableMultiTypeFilterDiv<R, EmfDataTableEnumFilterType> {

	private static final int MAX_NUM_DISTINCT_COLUMN_VALUES = 1000;

	private final IEmfDataTableColumn<R, T> column;
	private final EmfDataTableEnumFilterTypeSelect filterTypeSelect;
	private final DomEnumSelect<T> enumSelect;

	public EmfDataTableEnumFilterNode(IEmfDataTableColumn<R, T> column) {

		this(column, dummy -> new DomEnumSelect<>());
	}

	public EmfDataTableEnumFilterNode(IEmfDataTableColumn<R, T> column, Function<IEmfDataTableColumn<R, T>, DomEnumSelect<T>> enumSelectFactory) {

		this.column = column;
		this.filterTypeSelect = new EmfDataTableEnumFilterTypeSelect();
		this.enumSelect = enumSelectFactory.apply(column);
		this.enumSelect.addNilValue(DomI18n.PLEASE_SELECT.encloseInBrackets());
		this.enumSelect
			.addValuesSortedByDisplayString(
				column//
					.getDistinctColumnValues(MAX_NUM_DISTINCT_COLUMN_VALUES)
					.stream()
					.filter(value -> value != null)
					.collect(Collectors.toList()));
		this.enumSelect.addMarker(EmfTestMarker.DATA_TABLE_FILTER_INPUT_ENUM);
		refresh();
	}

	@Override
	public IEmfDataTableFilter<R> createFilter() {

		T filterValue = enumSelect.getSelectedValue();
		EmfDataTableEnumFilterType filterType = filterTypeSelect.getSelectedValue();
		Resetter resetter = new Resetter(filterType, filterValue);
		if (filterValue != null) {
			switch (filterType) {
			case IS:
				return new EmfDataTableValueFilter<>(column, DataTableValueFilterOperator.EQUAL, filterValue, resetter);
			case IS_NOT:
				return new EmfDataTableValueFilter<>(column, DataTableValueFilterOperator.NOT_EQUAL, filterValue, resetter);
			}
			throw new SofticarUnknownEnumConstantException(filterType);
		} else {
			return new EmfDataTableNopFilter<>(resetter);
		}
	}

	@Override
	public IEmfDataTableFilterTypeSelect<EmfDataTableEnumFilterType> getFilterSelect() {

		return filterTypeSelect;
	}

	@Override
	public IDomNode getFilterInput(EmfDataTableEnumFilterType filterType) {

		return enumSelect;
	}

	private class Resetter implements INullaryVoidFunction {

		private final EmfDataTableEnumFilterType filterType;
		private final T filterValue;

		public Resetter(EmfDataTableEnumFilterType filterType, T filterValue) {

			this.filterType = filterType;
			this.filterValue = filterValue;
		}

		@Override
		public void apply() {

			filterTypeSelect.setSelectedValue(filterType);
			enumSelect.setSelectedValue(filterValue);
		}
	}
}
