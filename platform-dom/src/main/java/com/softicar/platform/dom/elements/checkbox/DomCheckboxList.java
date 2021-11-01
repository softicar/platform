package com.softicar.platform.dom.elements.checkbox;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;

/**
 * A container element that aligns {@link DomCheckbox} instances below one
 * another.
 */
public class DomCheckboxList extends DomDiv {

	public DomCheckboxList() {

		setCssClass(DomElementsCssClasses.DOM_CHECKBOX_LIST);
	}

	public DomCheckboxList add(DomCheckbox checkbox) {

		appendChild(checkbox);
		return this;
	}
}
