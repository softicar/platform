package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.element.DomElementTag;

/**
 * Abstract base class for HTML input elements.
 *
 * @author Oliver Richers
 */
public abstract class DomInput extends DomElement implements IDomEnableable, IDomFocusable {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.INPUT;
	}
}
