package com.softicar.platform.dom.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import java.util.Map.Entry;

class DomJsonObjectDisplay extends DomLabelGrid {

	public DomJsonObjectDisplay(JsonObject object) {

		addCssClass(DomCssClasses.DOM_JSON_OBJECT_DISPLAY);

		for (Entry<String, JsonElement> entry: object.entrySet()) {
			add(IDisplayString.create(entry.getKey()), DomJsonDisplayFactory.create(entry.getValue()));
		}
	}
}
