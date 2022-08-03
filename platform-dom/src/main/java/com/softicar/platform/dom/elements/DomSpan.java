package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * An HTML <i>span</i> element.
 *
 * @author Oliver Richers
 */
public class DomSpan extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.SPAN;
	}
}
