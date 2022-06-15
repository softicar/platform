package com.softicar.platform.dom.document;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.popup.compositor.IDomPopupContext;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * Represents the root node of the {@link IDomDocument}.
 * <p>
 * There can only be one body element per instance of {@link IDomDocument}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomBody extends DomParentElement implements IDomPopupContext {

	/**
	 * Constructs the document head for the given {@link IDomDocument}.
	 */
	public DomBody(IDomDocument document) {

		super(document, false);
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.BODY;
	}

	@Override
	public boolean isAppended() {

		return true;
	}
}
