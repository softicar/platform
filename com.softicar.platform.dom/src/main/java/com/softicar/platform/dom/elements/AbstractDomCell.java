package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This is a abstract base class of {@link DomCell} and {@link DomHeaderCell}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDomCell extends DomParentElement {

	public AbstractDomCell setColSpan(int colSpan) {

		setAttribute("colspan", colSpan);
		return this;
	}

	public AbstractDomCell setRowSpan(int colSpan) {

		setAttribute("rowspan", colSpan);
		return this;
	}
}
