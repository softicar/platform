package com.softicar.platform.dom.parent;

import com.softicar.platform.dom.element.DomElementTag;

class TestElement extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.DIV;
	}
}
