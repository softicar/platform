package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html code element.
 * 
 * @author Oliver Richers
 */
public class DomCode extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.CODE;
	}
}
