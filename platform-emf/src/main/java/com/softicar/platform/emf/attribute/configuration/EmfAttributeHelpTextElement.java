package com.softicar.platform.emf.attribute.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.wiki.DomWikiDiv;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.emf.EmfCssClasses;
import java.util.Objects;
import java.util.function.Supplier;

class EmfAttributeHelpTextElement extends DomDiv {

	public EmfAttributeHelpTextElement(Supplier<DomWikiDivBuilder> wikiDivBuilderFactory) {

		this();
		appendChild(wikiDivBuilderFactory.get().build());
	}

	public EmfAttributeHelpTextElement(IDisplayString text, boolean wikiSyntax) {

		this();

		Objects.requireNonNull(text);
		if (wikiSyntax) {
			appendChild(new DomWikiDiv(text.toString()));
		} else {
			appendText(text);
		}
	}

	private EmfAttributeHelpTextElement() {

		addCssClass(EmfCssClasses.EMF_ATTRIBUTE_VALUE_HELP_TEXT_ELEMENT);
	}
}
