package com.softicar.platform.dom.document;

import com.softicar.platform.dom.node.IDomNode;

/**
 * Generates IDs for {@link IDomNode} instances.
 *
 * @author Oliver Richers
 */
class DomNodeIdGenerator {

	private int counter;

	public int generateId() {

		return counter++;
	}
}
