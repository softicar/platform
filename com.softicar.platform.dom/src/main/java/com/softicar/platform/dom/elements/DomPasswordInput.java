package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.input.DomTextInput;

/**
 * Input element for passwords. You see not the letters you enter but asterisks.
 * 
 * @author Thees Koester
 */
public class DomPasswordInput extends DomTextInput {

	public DomPasswordInput() {

		setAttribute("type", "password");
		getAccessor().setAttributeInMap("value", "");
	}
}
