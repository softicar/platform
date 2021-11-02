package com.softicar.platform.emf.attribute.field.foreign.entity.collection.list;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.collection.list.IEmfEntityList;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EmfForeignEntityListDisplay<E extends IEmfEntity<E, ?>> extends DomDiv {

	public EmfForeignEntityListDisplay(IEmfEntityList<?, E> entityList) {

		List<E> elements = Optional//
			.ofNullable(entityList)
			.map(it -> it.getElements())
			.orElse(Collections.emptyList());

		if (!elements.isEmpty()) {
			for (int i = 0; i < elements.size(); i++) {
				E entity = elements.get(i);
				appendText(
					new DisplayString()//
						.append(String.valueOf(i))
						.append(". ")
						.append(entity.toDisplay()));
				appendNewChild(DomElementTag.BR);
			}
		}
	}
}
