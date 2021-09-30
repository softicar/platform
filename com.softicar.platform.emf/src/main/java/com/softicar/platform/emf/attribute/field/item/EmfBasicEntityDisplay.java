package com.softicar.platform.emf.attribute.field.item;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.dom.elements.DomDiv;

public class EmfBasicEntityDisplay<I extends IEntity> extends DomDiv {

	public EmfBasicEntityDisplay(I value) {

		appendText(value.toDisplay());
	}
}
