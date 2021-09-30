package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.AbstractEmfDataTableMultiTypeFilterDiv;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterTypeSelect;
import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;

public class EmfDataTableStringFilterNode<R> extends AbstractEmfDataTableMultiTypeFilterDiv<R, EmfDataTableStringFilterType> {

	private final IEmfDataTableColumn<R, String> column;
	private final EmfDataTableStringFilterTypeSelect filterTypeSelect;
	private final EmfDataTableStringFilterInput filterInput;

	public EmfDataTableStringFilterNode(IEmfDataTableColumn<R, String> column) {

		this.column = column;
		this.filterTypeSelect = new EmfDataTableStringFilterTypeSelect(this);
		this.filterInput = new EmfDataTableStringFilterInput();

		refresh();
	}

	@Override
	public IEmfDataTableFilter<R> createFilter() {

		String filterText = this.filterInput.getTextOrNull();
		EmfDataTableStringFilterType filterType = filterTypeSelect.getSelectedValue();
		Resetter resetter = new Resetter(filterType, filterText);
		if (filterText != null) {
			switch (filterType) {
			case CONTAINS_WORDS:
				return new ContainsWordsFilter<>(column.getDataColumn(), filterText, resetter);
			case CONTAINS_TEXT:
				return new ContainsTextFilter<>(column.getDataColumn(), filterText, resetter);
			case EQUALS_TEXT:
				return new EqualsTextFilter<>(column.getDataColumn(), filterText, resetter);
			case MATCHES_REGEXP:
				return new MatchesRegexpFilter<>(column.getDataColumn(), filterText, resetter);
			}

			throw new SofticarUnknownEnumConstantException(filterType);
		} else {
			return new EmfDataTableNopFilter<>(resetter);
		}
	}

	@Override
	public void selectFirstInputElement() {

		this.filterInput.select();
	}

	@Override
	public IEmfDataTableFilterTypeSelect<EmfDataTableStringFilterType> getFilterSelect() {

		return this.filterTypeSelect;
	}

	@Override
	public IDomNode getFilterInput(EmfDataTableStringFilterType filterType) {

		return this.filterInput;
	}

	private class Resetter implements INullaryVoidFunction {

		private final EmfDataTableStringFilterType filterType;
		private final String filterText;

		public Resetter(EmfDataTableStringFilterType filterType, String filterText) {

			this.filterType = filterType;
			this.filterText = filterText;
		}

		@Override
		public void apply() {

			filterTypeSelect.setSelectedValue(filterType);
			filterInput.setValue(filterText);
		}
	}
}
