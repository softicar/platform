package com.softicar.platform.dom.json;

import com.google.gson.JsonPrimitive;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;

class DomJsonNumberDisplay extends DomDiv {

	public DomJsonNumberDisplay(JsonPrimitive primtive) {

		addCssClass(DomCssClasses.DOM_JSON_NUMBER_DISPLAY);
		appendText(primtive.getAsString());
	}
}
