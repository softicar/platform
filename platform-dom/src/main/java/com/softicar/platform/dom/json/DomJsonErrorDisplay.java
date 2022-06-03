package com.softicar.platform.dom.json;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;

class DomJsonErrorDisplay extends DomDiv {

	public DomJsonErrorDisplay() {

		addCssClass(DomCssClasses.DOM_JSON_ERROR_DISPLAY);
		addCssClass(DomCssPseudoClasses.ERROR);
		appendText("(json error)");
	}
}
