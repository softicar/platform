package com.softicar.platform.emf.data.table.column.handler.collection;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.data.table.column.handler.filtering.IEmfDataTableColumnFilterNodeFactory;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterNode;
import com.softicar.platform.emf.data.table.filter.collection.EmfDataTableCollectionFilterNode;

public abstract class AbstractEmfDataTableCollectionColumnHandler<C, E>
		implements IEmfDataTableValueBasedColumnHandler<C>, IEmfDataTableCollectionAdapter<C, E> {

	@Override
	public void buildCell(IEmfDataTableCell<?, C> cell, C collection) {

		for (E element: getElements(collection)) {
			cell.appendText(getDisplayStringWithoutId(element));
			cell.appendNewChild(DomElementTag.BR);
		}
	}

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, C> column) {

		return false;
	}

	@Override
	public boolean isReverseOrderDirection() {

		return false;
	}

	@Override
	public IEmfDataTableColumnFilterNodeFactory<C> getFilterNodeFactory(IEmfDataTableColumn<?, C> column) {

		return this::createFilterNode;
	}

	private <R> IEmfDataTableFilterNode<R> createFilterNode(IEmfDataTableColumn<R, C> column) {

		return new EmfDataTableCollectionFilterNode<>(column, this);
	}
}
