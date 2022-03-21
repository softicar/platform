package com.softicar.platform.emf.data.table.filter.bool;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterNode;
import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableValueFilter;

public class EmfDataTableBooleanFilterNode<R> extends DomDiv implements IEmfDataTableFilterNode<R> {

	private final IEmfDataTableColumn<R, Boolean> column;
	private final BooleanSelect booleanSelect;

	public EmfDataTableBooleanFilterNode(IEmfDataTableColumn<R, Boolean> column) {

		this.column = column;
		this.booleanSelect = new BooleanSelect();

		appendChild(booleanSelect);
	}

	@Override
	public IEmfDataTableFilter<R> createFilter() {

		Boolean value = booleanSelect.getSelectedValue();
		Resetter resetter = new Resetter(value);
		if (value != null) {
			return new EmfDataTableValueFilter<>(column, DataTableValueFilterOperator.EQUAL, value, resetter);
		} else {
			return new EmfDataTableNopFilter<>(resetter);
		}
	}

	private class Resetter implements INullaryVoidFunction {

		private final Boolean value;

		public Resetter(Boolean value) {

			this.value = value;
		}

		@Override
		public void apply() {

			booleanSelect.setSelectedValue(value);
		}
	}
}
