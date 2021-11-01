package com.softicar.platform.dom.elements.testing.node.iterable;

import com.softicar.platform.common.container.iterable.recurse.RecurseIterable;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.Collections;

/**
 * A {@link RecurseIterable} for the children of an {@link IDomNode}.
 *
 * @author Oliver Richers
 */
class DomNodeRecursiveIterable extends RecurseIterable<IDomNode> {

	public DomNodeRecursiveIterable(IDomNode root) {

		super(getChildren(root), DomNodeRecursiveIterable::getChildren);
	}

	private static Iterable<IDomNode> getChildren(IDomNode node) {

		return CastUtils//
			.tryCast(node, IDomParentElement.class)
			.map(IDomParentElement::getChildren)
			.orElse(Collections.emptyList());
	}
}
