package com.softicar.platform.dom.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.softicar.platform.dom.elements.DomDiv;

public class DomJsonDisplayFactory {

	public static DomDiv create(String jsonText) {

		return create(JsonParser.parseString(jsonText));
	}

	public static DomDiv create(JsonElement element) {

		if (element.isJsonArray()) {
			return new DomJsonArrayDisplay(element.getAsJsonArray());
		} else if (element.isJsonObject()) {
			return new DomJsonObjectDisplay(element.getAsJsonObject());
		} else if (element.isJsonPrimitive()) {
			return new DomJsonStringDisplay(element.getAsJsonPrimitive());
		} else if (element.isJsonNull()) {
			return new DomJsonNullDisplay();
		} else {
			return new DomJsonErrorDisplay();
		}
	}
}
