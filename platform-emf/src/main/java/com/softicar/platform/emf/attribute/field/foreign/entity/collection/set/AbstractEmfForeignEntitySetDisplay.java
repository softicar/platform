package com.softicar.platform.emf.attribute.field.foreign.entity.collection.set;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.collection.set.IEmfEntitySet;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractEmfForeignEntitySetDisplay<E extends IEmfEntity<E, ?>> extends DomDiv {

	public AbstractEmfForeignEntitySetDisplay(IEmfEntitySet<?, E> entitySet) {

		List<E> elements = loadElements(entitySet);
		if (!elements.isEmpty()) {
			displayElements(elements);
		}
	}

	protected abstract void displayElements(List<E> elements);

	private List<E> loadElements(IEmfEntitySet<?, E> entitySet) {

		if (entitySet != null) {
			return entitySet//
				.getElements()
				.stream()
				.sorted(Comparator.comparing(IEmfTableRow::toDisplay))
				.collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}
}
