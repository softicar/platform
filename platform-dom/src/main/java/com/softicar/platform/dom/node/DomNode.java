package com.softicar.platform.dom.node;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;

/**
 * Represents a tree node of an {@link IDomDocument}.
 *
 * @author Oliver Richers
 */
public abstract class DomNode extends AbstractDomNode {

	// -------------------- BASIC METHODS -------------------- //

	protected DomNode() {

		super(CurrentDomDocument.get());
	}

	protected DomNode(IDomDocument document) {

		super(document);
	}
}
