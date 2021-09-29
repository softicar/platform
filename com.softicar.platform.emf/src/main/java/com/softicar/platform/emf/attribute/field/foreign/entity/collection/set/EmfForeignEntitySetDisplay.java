package com.softicar.platform.emf.attribute.field.foreign.entity.collection.set;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.emf.collection.set.IEmfEntitySet;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.List;

public class EmfForeignEntitySetDisplay<E extends IEmfEntity<E, ?>> extends AbstractEmfForeignEntitySetDisplay<E> {

	public EmfForeignEntitySetDisplay(IEmfEntitySet<?, E> entitySet) {

		super(entitySet);
	}

	@Override
	protected void displayElements(List<E> elements) {

		for (E entity: elements) {
			appendText(entity.toDisplay());
			appendNewChild(DomElementTag.BR);
		}
	}
}
