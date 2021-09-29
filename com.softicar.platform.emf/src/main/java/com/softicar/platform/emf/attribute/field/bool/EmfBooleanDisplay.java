package com.softicar.platform.emf.attribute.field.bool;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;

public class EmfBooleanDisplay extends DomDiv {

	public EmfBooleanDisplay(Boolean value) {

		addCssClass(EmfCssClasses.EMF_BOOLEAN_DISPLAY);
		addCssClass(value? EmfCssClasses.EMF_BOOLEAN_DISPLAY_TRUE : EmfCssClasses.EMF_BOOLEAN_DISPLAY_FALSE);
		appendText(value? EmfI18n.YES : EmfI18n.NO);
	}
}
