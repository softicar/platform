package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.DomElementTag;
import java.util.Optional;

public abstract class AbstractDomValueInputDiv<V> extends AbstractDomValueInputElement<V> {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.DIV;
	}

	public abstract Optional<IDomTextualInput> getInputField();
}
