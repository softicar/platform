package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;

public abstract class AbstractEmfInputDiv<T> extends DomDiv implements IEmfInput<T> {

	public AbstractEmfInputDiv() {

		addCssClass(EmfCssClasses.EMF_INPUT_DIV);
	}

	@Override
	public T getValue() throws EmfInputException {

		return getValueAsOptional().orElse(null);
	}
}
