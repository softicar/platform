package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html element for pre-formatted text.
 * 
 * @author Oliver Richers
 */
public class DomPre extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.PRE;
	}
}
