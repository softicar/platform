package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomImage;

public class DomAutoCompleteIndicator extends DomImage {

	public DomAutoCompleteIndicator(DomAutoCompleteIndicatorType type) {

		super(type.getImage());

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR);
		addCssClass(type.getCssClass());

		setTitle(type.getTitle());
	}
}
