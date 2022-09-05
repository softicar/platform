package com.softicar.platform.dom.elements.checkbox;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;

/**
 * A container element that aligns {@link DomCheckbox} instances below one
 * another.
 */
public class DomCheckboxList extends DomDiv {

	public DomCheckboxList() {

		setCssClass(DomCssClasses.DOM_CHECKBOX_LIST);
	}

	public DomCheckboxList add(DomCheckbox checkbox) {

		appendChild(checkbox);
		return this;
	}
}
