package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.input.IDomInput;
import com.softicar.platform.emf.EmfCssClasses;

public abstract class AbstractEmfInputDiv<T> extends DomDiv implements IEmfInput<T> {

	public AbstractEmfInputDiv() {

		addCssClass(EmfCssClasses.EMF_INPUT_DIV);
	}

	@Override
	public final IDomInput setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	@Override
	public final boolean isEnabled() {

		return !isDisabled();
	}
}
