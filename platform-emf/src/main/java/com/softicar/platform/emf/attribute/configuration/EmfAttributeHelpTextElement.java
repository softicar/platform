package com.softicar.platform.emf.attribute.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;

class EmfAttributeHelpTextElement extends DomDiv {

	public EmfAttributeHelpTextElement(IDisplayString helpText) {

		addCssClass(EmfCssClasses.EMF_ATTRIBUTE_VALUE_HELP_TEXT_ELEMENT);
		appendText(helpText);
	}
}
