package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html division element.
 * 
 * @author Oliver Richers
 */
public class DomDiv extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.DIV;
	}
}
