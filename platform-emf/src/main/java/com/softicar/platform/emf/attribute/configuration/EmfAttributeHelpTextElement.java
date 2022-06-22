package com.softicar.platform.emf.attribute.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.wiki.DomWikiDiv;
import com.softicar.platform.emf.EmfCssClasses;
import java.util.Objects;

class EmfAttributeHelpTextElement extends DomDiv {

	public EmfAttributeHelpTextElement(IDisplayString text, boolean wikiSyntax) {

		Objects.requireNonNull(text);

		if (wikiSyntax) {
			appendChild(new DomWikiDiv(text.toString()));
		} else {
			appendText(text);
		}

		addCssClass(EmfCssClasses.EMF_ATTRIBUTE_VALUE_HELP_TEXT_ELEMENT);
	}
}
