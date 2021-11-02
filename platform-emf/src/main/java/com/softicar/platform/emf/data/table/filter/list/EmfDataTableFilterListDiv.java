package com.softicar.platform.emf.data.table.filter.list;

import com.softicar.platform.common.container.data.table.DataTableFilterListOperator;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterNode;
import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmfDataTableFilterListDiv<R> extends DomDiv implements IEmfDataTableFilterNode<R> {

	private final IEmfDataTableColumn<R, ?> column;
	private final FilterElementListModel<R> model;

	public EmfDataTableFilterListDiv(IEmfDataTableColumn<R, ?> column) {

		this.column = column;
		this.model = new FilterElementListModel<>();
		this.model.getFilterElementList().add(new EmfDataTableFilterListElementDiv<>(column, this::removeFilter));

		addCssClass(EmfCssClasses.EMF_DATA_TABLE_FILTER_LIST_DIV);
	}

	@Override
	public void selectFirstInputElement() {

		EmfDataTableFilterListElementDiv<R, ?> filterElement = getFirstFilterElementOrNull();
		if (filterElement != null) {
			filterElement.getFilterElement().selectFirstInputElement();
		}
	}

	@Override
	public IEmfDataTableFilter<R> createFilter() {

		FilterElementList<R> filterElementList = model.getFilterElementList();
		DataTableFilterListOperator operator = model.getOperator();

		filterElementList.markUnsavedElementsAsSaved();

		List<EmfDataTableFilterListElementDiv<R, ?>> nopFilterElements = new ArrayList<>();
		EmfDataTableFilterList<R> filterList = new EmfDataTableFilterList<>(operator);
		for (EmfDataTableFilterListElementDiv<R, ?> filterElement: filterElementList.getSavedElements()) {
			IEmfDataTableFilter<R> filter = filterElement.getFilterElement().createFilter();
			if (filter instanceof EmfDataTableNopFilter) {
				nopFilterElements.add(filterElement);
			} else {
				filterList.addFilter(filter);
			}
		}

		filterElementList.removeElements(nopFilterElements);
		filterElementList.removeUnsavedAndDeletedElements();

		if (!filterList.isEmpty()) {
			return filterList;
		} else {
			return new EmfDataTableNopFilter<>();
		}
	}

	public void updateAndRebuildFilterElements() {

		model.getFilterElementList().removeUnsavedAndRestoreDeletedElements();
		rebuildFilterElements();
	}

	private void rebuildFilterElements() {

		removeChildren();

		FilterElementList<R> filterElementList = model.getFilterElementList();
		List<EmfDataTableFilterListElementDiv<R, ?>> filterElements = filterElementList.getSavedAndUnsavedElements();
		if (filterElements.isEmpty()) {
			EmfDataTableFilterListElementDiv<R, ?> initialElement = new EmfDataTableFilterListElementDiv<>(column, this::removeFilter);
			filterElements.add(initialElement);
			filterElementList.add(initialElement);
		}

		for (int i = 0; i < filterElements.size(); i++) {
			appendChild(filterElements.get(i));
			if (i < filterElements.size() - 1) {
				appendChild(new DomActionBar(new OperatorButton(model, this::toggleOperator)));
			} else {
				appendChild(new DomActionBar(new FilterElementAddButton(this::addFilterElement)));
			}
		}
	}

	private void addFilterElement() {

		model.getFilterElementList().add(new EmfDataTableFilterListElementDiv<>(column, this::removeFilter));
		rebuildFilterElements();
	}

	private void removeFilter(EmfDataTableFilterListElementDiv<R, ?> filterElement) {

		filterElement.setState(FilterElementState.DELETED);
		rebuildFilterElements();
	}

	private void toggleOperator() {

		if (model.getOperator() == DataTableFilterListOperator.AND) {
			model.setOperator(DataTableFilterListOperator.OR);
		} else {
			model.setOperator(DataTableFilterListOperator.AND);
		}

		// FIXME this is utterly stupid
		getChildren()//
			.stream()
			.filter(DomActionBar.class::isInstance)
			.map(DomActionBar.class::cast)
			.map(DomActionBar::getChildren)
			.flatMap(Collection::stream)
			.filter(OperatorButton.class::isInstance)
			.map(OperatorButton.class::cast)
			.forEach(OperatorButton::refresh);
	}

	private EmfDataTableFilterListElementDiv<R, ?> getFirstFilterElementOrNull() {

		List<EmfDataTableFilterListElementDiv<R, ?>> filterElements = model.getFilterElementList().getSavedAndUnsavedElements();
		return !filterElements.isEmpty()? filterElements.iterator().next() : null;
	}
}
