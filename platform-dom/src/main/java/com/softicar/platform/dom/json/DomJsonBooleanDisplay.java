package com.softicar.platform.dom.json;

import com.google.gson.JsonPrimitive;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;

class DomJsonBooleanDisplay extends DomDiv {

	public DomJsonBooleanDisplay(JsonPrimitive primitive) {

		addCssClass(DomCssClasses.DOM_JSON_BOOLEAN_DISPLAY);
		appendText(primitive.getAsString());
	}
}
