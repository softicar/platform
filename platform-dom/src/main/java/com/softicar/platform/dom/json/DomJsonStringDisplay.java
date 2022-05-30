package com.softicar.platform.dom.json;

import com.google.gson.JsonPrimitive;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.text.DomMultilineStringDisplay;

class DomJsonStringDisplay extends DomDiv {

	public DomJsonStringDisplay(JsonPrimitive primitive) {

		addCssClass(DomCssClasses.DOM_JSON_STRING_DISPLAY);

		var text = primitive.getAsString();
		if (text.contains("\n")) {
			appendChild(new DomMultilineStringDisplay(text));
		} else {
			appendText(text);
		}
	}
}
