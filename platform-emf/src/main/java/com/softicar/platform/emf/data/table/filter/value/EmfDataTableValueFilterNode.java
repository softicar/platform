package com.softicar.platform.emf.data.table.filter.value;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.input.IDomValueInput;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.AbstractEmfDataTableMultiTypeFilterDiv;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;
import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;
import java.util.function.Supplier;

public class EmfDataTableValueFilterNode<R, T> extends AbstractEmfDataTableMultiTypeFilterDiv<R, DataTableValueFilterOperator> {

	private final IEmfDataTableColumn<R, T> column;
	private final EmfDataTableValueFilterOperatorSelect filterOperatorSelect;
	private final IDomValueInput<T> valueFilterInput;

	public EmfDataTableValueFilterNode(IEmfDataTableColumn<R, T> column, Supplier<IDomValueInput<T>> inputFactory) {

		this.column = column;
		this.filterOperatorSelect = new EmfDataTableValueFilterOperatorSelect(this);
		this.valueFilterInput = inputFactory.get();
		this.valueFilterInput.addMarker(EmfDataTableDivMarker.FILTER_INPUT_VALUE);

		refresh();
	}

	@Override
	public IEmfDataTableFilter<R> createFilter() {

		DataTableValueFilterOperator filterOperator = filterOperatorSelect.getSelectedValue();
		T filterValue = valueFilterInput.getValue().orElse(null);
		Resetter resetter = new Resetter(filterOperator, filterValue);

		if (filterValue != null) {
			return new EmfDataTableValueFilter<>(column, filterOperator, filterValue, resetter);
		} else {
			return new EmfDataTableNopFilter<>(resetter);
		}
	}

	@Override
	public IEmfDataTableFilterTypeSelect<DataTableValueFilterOperator> getFilterSelect() {

		return filterOperatorSelect;
	}

	@Override
	public IDomValueInput<T> getFilterInput(DataTableValueFilterOperator filterType) {

		return valueFilterInput;
	}

	private class Resetter implements INullaryVoidFunction {

		private final DataTableValueFilterOperator filterOperator;
		private final T filterValue;

		public Resetter(DataTableValueFilterOperator filterOperator, T filterValue) {

			this.filterOperator = filterOperator;
			this.filterValue = filterValue;
		}

		@Override
		public void apply() {

			valueFilterInput.setValue(filterValue);
			filterOperatorSelect.setSelectedType(filterOperator);
		}
	}
}
