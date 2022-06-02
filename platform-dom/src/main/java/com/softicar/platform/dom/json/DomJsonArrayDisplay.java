package com.softicar.platform.dom.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;

class DomJsonArrayDisplay extends DomDiv {

	public DomJsonArrayDisplay(JsonArray array) {

		addCssClass(DomCssClasses.DOM_JSON_ARRAY_DISPLAY);

		for (JsonElement element: array) {
			appendChild(DomJsonDisplayFactory.create(element));
		}
	}
}
