package com.softicar.platform.dom.document;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * Represents the head node of the {@link IDomDocument}.
 * <p>
 * There can only be one head element per instance of {@link IDomDocument}.
 *
 * @author Oliver Richers
 */
public class DomHead extends DomParentElement {

	/**
	 * Constructs the document head for the given {@link IDomDocument}.
	 */
	public DomHead(IDomDocument document) {

		super(document, false);
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.HEAD;
	}
}
