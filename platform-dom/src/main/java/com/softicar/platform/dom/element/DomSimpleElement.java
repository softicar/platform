package com.softicar.platform.dom.element;

import com.softicar.platform.dom.parent.DomParentElement;
import com.softicar.platform.dom.parent.IDomParentElement;

/**
 * Very simple dom element.
 * <p>
 * This class is used to insert horizontal lines, line breaks, paragraphs or
 * head lines. It is usually not used directly, but indirectly using
 * {@link IDomParentElement#appendNewChild(DomElementTag)}.
 * 
 * @author Oliver Richers
 */
public final class DomSimpleElement extends DomParentElement {

	private final DomElementTag tag;

	public DomSimpleElement(DomElementTag tag) {

		super(false); // postpone creation until field is initialized
		this.tag = tag;

		createElement();
	}

	@Override
	public DomElementTag getTag() {

		return tag;
	}
}
