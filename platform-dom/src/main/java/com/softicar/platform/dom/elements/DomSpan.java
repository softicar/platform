package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * An HTML <i>span</i> element.
 *
 * @author Oliver Richers
 */
public class DomSpan extends DomParentElement {

	public DomSpan() {

		// nothing to do
	}

	public DomSpan(String text) {

		appendText(text);
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.SPAN;
	}
}
