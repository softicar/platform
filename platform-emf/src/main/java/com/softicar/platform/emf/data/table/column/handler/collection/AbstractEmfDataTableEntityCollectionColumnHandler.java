package com.softicar.platform.emf.data.table.column.handler.collection;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInput;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import java.util.Collection;
import java.util.function.Supplier;

public abstract class AbstractEmfDataTableEntityCollectionColumnHandler<C, E extends IEntity> extends AbstractEmfDataTableCollectionColumnHandler<C, E> {

	@Override
	public void buildCell(IEmfDataTableCell<?, C> cell, C collection) {

		boolean showIds = cell.getColumn().getSettings().isShowIds();
		for (E element: getElements(collection)) {
			cell.appendText(showIds? element.toDisplay() : element.toDisplayWithoutId());
			cell.appendNewChild(DomElementTag.BR);
		}
	}

	@Override
	public IDisplayString getDisplayStringWithoutId(E element) {

		return element.toDisplayWithoutId();
	}

	@Override
	public IDomAutoCompleteInput<E> createInputNode(Supplier<Collection<E>> validElementsSupplier) {

		return new DomAutoCompleteInput<>(validElementsSupplier);
	}
}
