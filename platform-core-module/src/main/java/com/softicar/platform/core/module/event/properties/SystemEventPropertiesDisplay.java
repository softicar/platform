package com.softicar.platform.core.module.event.properties;

import com.google.gson.JsonParser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.json.DomJsonDisplayFactory;

public class SystemEventPropertiesDisplay extends DomDiv {

	public SystemEventPropertiesDisplay(String jsonText) {

		var jsonElement = JsonParser.parseString(jsonText);
		appendChild(DomJsonDisplayFactory.create(jsonElement));
	}
}
