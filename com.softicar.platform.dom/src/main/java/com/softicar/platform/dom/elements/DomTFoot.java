package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html table footer element.
 * 
 * @author Oliver Richers
 */
public class DomTFoot extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.TFOOT;
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
