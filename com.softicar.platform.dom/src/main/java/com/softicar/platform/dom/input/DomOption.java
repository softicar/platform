package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * Represents an option of {@link DomSelect}.
 * 
 * @author Oliver Richers
 */
public class DomOption extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.OPTION;
	}
}
