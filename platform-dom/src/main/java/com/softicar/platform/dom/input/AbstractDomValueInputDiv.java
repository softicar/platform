package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.DomElementTag;

public abstract class AbstractDomValueInputDiv<V> extends AbstractDomValueInputElement<V> {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.DIV;
	}
}
