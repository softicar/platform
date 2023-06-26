package com.softicar.platform.emf.data.table.filter.value;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomSpan;
import com.softicar.platform.dom.input.IDomValueInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfTestMarker;
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
	private final DomSpan dummyInput;

	public EmfDataTableValueFilterNode(IEmfDataTableColumn<R, T> column, Supplier<IDomValueInput<T>> inputFactory) {

		this.column = column;
		this.filterOperatorSelect = new EmfDataTableValueFilterOperatorSelect(this);
		this.valueFilterInput = inputFactory.get();
		this.valueFilterInput.addMarker(EmfTestMarker.DATA_TABLE_FILTER_INPUT_VALUE);
		this.dummyInput = new DomSpan();
		refresh();
	}

	@Override
	public IEmfDataTableFilter<R> createFilter() {

		DataTableValueFilterOperator filterOperator = filterOperatorSelect.getSelectedValue();

		return switch (filterOperator) {
		case EMPTY -> new EmfDataTableEmptyValueFilter<>(column.getDataColumn(), new Resetter(filterOperator, null));
		case NOT_EMPTY -> new EmfDataTableNotEmptyValueFilter<>(column.getDataColumn(), new Resetter(filterOperator, null));
		default -> createFilter(filterOperator);
		};
	}

	private IEmfDataTableFilter<R> createFilter(DataTableValueFilterOperator filterOperator) {

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
	public IDomNode getFilterInput(DataTableValueFilterOperator filterType) {

		return switch (filterType) {
		case EMPTY -> dummyInput;
		case NOT_EMPTY -> dummyInput;
		default -> valueFilterInput;
		};
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
