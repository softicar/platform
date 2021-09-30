package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * A legend displays a label in the upper left part of a {@link DomFieldset}.
 *
 * @author Boris Schaa
 * @author Oliver Richers
 */
public class DomLegend extends DomParentElement {

	public DomLegend() {

		// nothing to do
	}

	public DomLegend(IDisplayString legendText) {

		appendText(legendText);
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.LEGEND;
	}

	public void setLegend(IDisplayString legendText) {

		removeChildren();
		appendText(legendText);
	}
}
