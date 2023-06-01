package com.softicar.platform.emf.data.table.filter;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfCssClasses;
import java.util.Objects;

public abstract class AbstractEmfDataTableMultiTypeFilterDiv<R, FilterType> extends DomDiv implements IEmfDataTableFilterNode<R>, IRefreshable {

	private IEmfDataTableFilterTypeSelect<FilterType> filterSelect;
	private FilterType filterType;
	private IDomNode filterInput;

	public AbstractEmfDataTableMultiTypeFilterDiv() {

		addCssClass(EmfCssClasses.EMF_DATA_TABLE_FILTER_LIST_ELEMENT_DIV);
	}

	@Override
	public void refresh() {

		// append filter select if not done so, yet
		if (this.filterSelect == null) {
			this.filterSelect = getFilterSelect();
			appendChild(filterSelect);
		}

		// append filter input if not done so, yet
		if (this.filterInput == null) {
			appendFilterInput();
		} else {
			// refresh filter input if necessary
			FilterType filterType = filterSelect.getSelectedType();
			if (!Objects.equals(filterType, this.filterType)) {
				removeChild(filterInput);
				appendFilterInput();
			}
		}
	}

	private void appendFilterInput() {

		this.filterType = filterSelect.getSelectedType();
		this.filterInput = getFilterInput(this.filterType);

		appendChild(filterInput);
	}

	public abstract IEmfDataTableFilterTypeSelect<FilterType> getFilterSelect();

	public abstract IDomNode getFilterInput(FilterType filterType);
}
