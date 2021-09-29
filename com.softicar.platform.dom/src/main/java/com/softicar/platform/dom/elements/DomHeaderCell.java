package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;

/**
 * This class represents an Html table header cell element.
 *
 * @author Oliver Richers
 */
public class DomHeaderCell extends AbstractDomCell {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.TH;
	}
}
