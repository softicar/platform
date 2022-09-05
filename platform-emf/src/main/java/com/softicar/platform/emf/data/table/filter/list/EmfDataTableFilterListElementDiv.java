package com.softicar.platform.emf.data.table.filter.list;

import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterNode;
import java.util.Objects;
import java.util.function.Consumer;

class EmfDataTableFilterListElementDiv<R, T> extends DomDiv {

	private final IEmfDataTableFilterNode<R> filterElement;
	private final Consumer<EmfDataTableFilterListElementDiv<R, T>> filterElementRemover;
	private FilterElementState state;

	public EmfDataTableFilterListElementDiv(IEmfDataTableColumn<R, T> column, Consumer<EmfDataTableFilterListElementDiv<R, T>> filterElementRemover) {

		this.filterElement = column.getColumnHandler().getFilterNodeFactory(column).createFilterNode();
		this.filterElementRemover = Objects.requireNonNull(filterElementRemover);
		this.state = FilterElementState.UNSAVED;

		addCssClass(EmfCssClasses.EMF_DATA_TABLE_FILTER_LIST_ELEMENT_DIV);
		appendChild(getFilterElement());
		appendChild(new DomActionBar(new RemoveFilterButton()));
	}

	public IEmfDataTableFilterNode<R> getFilterElement() {

		return filterElement;
	}

	public void setState(FilterElementState state) {

		this.state = state;
	}

	public FilterElementState getState() {

		return state;
	}

	private class RemoveFilterButton extends DomButton {

		public RemoveFilterButton() {

			setIcon(DomImages.FILTER_REMOVE.getResource());
			setTitle(EmfDataTableI18n.REMOVE_FILTER);
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			filterElementRemover.accept(EmfDataTableFilterListElementDiv.this);
		}
	}
}
