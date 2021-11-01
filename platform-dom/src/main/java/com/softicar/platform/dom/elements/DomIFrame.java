package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html inline frame element.
 * 
 * @author Oliver Richers
 */
public class DomIFrame extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.IFRAME;
	}

	public void setAddress(String address) {

		setAttribute("src", address);
	}
}
