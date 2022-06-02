package com.softicar.platform.dom.json;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;

class DomJsonNullDisplay extends DomDiv {

	public DomJsonNullDisplay() {

		addCssClass(DomCssClasses.DOM_JSON_NULL_DISPLAY);
		appendText("null");
	}
}
