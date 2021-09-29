package com.softicar.platform.emf.data.table.filter.entity;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelect;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.AbstractEmfDataTableMultiTypeFilterDiv;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;
import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableValueFilter;

public class EmfDataTableEntityFilterNode<R, T extends IEntity> extends AbstractEmfDataTableMultiTypeFilterDiv<R, EmfDataTableEntityFilterType> {

	private final IEmfDataTableColumn<R, T> column;
	private final EmfDataTableEntityFilterTypeSelect filterTypeSelect;
	private final DomSimpleValueSelect<T> select;

	public EmfDataTableEntityFilterNode(IEmfDataTableColumn<R, T> column) {

		this.column = column;
		this.filterTypeSelect = new EmfDataTableEntityFilterTypeSelect();
		this.select = new EmfDataTableEntityValueSelectBuilder<>(column).build();
		this.select.setMarker(EmfDataTableDivMarker.FILTER_INPUT_ENTITY);

		refresh();
	}

	@Override
	public IEmfDataTableFilter<R> createFilter() {

		var filterValue = select.getSelectedValue();
		EmfDataTableEntityFilterType filterType = filterTypeSelect.getSelectedValue();
		Resetter resetter = new Resetter(filterType, filterValue.orElse(null));
		if (filterValue.isPresent()) {
			switch (filterType) {
			case IS:
				return new EmfDataTableValueFilter<>(column, DataTableValueFilterOperator.EQUAL, filterValue.get(), resetter);
			case IS_NOT:
				return new EmfDataTableValueFilter<>(column, DataTableValueFilterOperator.NOT_EQUAL, filterValue.get(), resetter);
			}
			throw new SofticarUnknownEnumConstantException(filterType);
		} else {
			return new EmfDataTableNopFilter<>(resetter);
		}
	}

	@Override
	public void selectFirstInputElement() {

		// no default focus
	}

	@Override
	public IEmfDataTableFilterTypeSelect<EmfDataTableEntityFilterType> getFilterSelect() {

		return filterTypeSelect;
	}

	@Override
	public IDomNode getFilterInput(EmfDataTableEntityFilterType filterType) {

		return select;
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
			select.selectValue(filterValue);
		}
	}
}
