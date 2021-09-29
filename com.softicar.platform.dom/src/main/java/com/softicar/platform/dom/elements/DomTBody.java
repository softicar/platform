package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html table body element.
 *
 * @author Oliver Richers
 */
public class DomTBody extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.TBODY;
	}

	public DomRow appendRow() {

		DomRow row = new DomRow();
		appendChild(row);
		return row;
	}

	public DomRow appendRow(Object...children) {

		DomRow row = appendRow();
		row.appendCells(children);
		return row;
	}
}
