package com.softicar.platform.dom.node.tree;

import com.softicar.platform.common.container.iterable.recurse.RecurseIterable;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Provides streaming support for a tree of {@link IDomNode} instances.
 *
 * @author Oliver Richers
 */
public class DomNodeTree {

	private final IDomNode rootNode;

	/**
	 * Constructs this {@link DomNodeTree} for the given root {@link IDomNode}.
	 *
	 * @param rootNode
	 *            the root {@link IDomNode} (never <i>null</i>)
	 */
	public DomNodeTree(IDomNode rootNode) {

		this.rootNode = rootNode;
	}

	/**
	 * Returns a {@link Stream} of all {@link IDomNode} objects.
	 * <p>
	 * The returned {@link Stream} is in pre-order traversal, and includes the
	 * root {@link IDomNode}.
	 *
	 * @return a {@link Stream} of all {@link IDomNode} objects in this
	 *         {@link DomNodeTree} (never <i>null</i>)
	 */
	public Stream<IDomNode> stream() {

		return StreamSupport.stream(getRecursiveIterable().spliterator(), false);
	}

	/**
	 * Returns a {@link Stream} of all {@link IDomNode} objects that are
	 * instances of the given {@link Class}.
	 * <p>
	 * The returned {@link Stream} is in pre-order traversal, and includes the
	 * root {@link IDomNode} if it is an instance of the given {@link Class}.
	 *
	 * @param targetClass
	 *            the target {@link Class} to check for (never <i>null</i>)
	 * @return a {@link Stream} of all matching {@link IDomNode} objects in this
	 *         {@link DomNodeTree} (never <i>null</i>)
	 */
	public <T> Stream<T> stream(Class<T> targetClass) {

		return stream().filter(targetClass::isInstance).map(targetClass::cast);
	}

	private Iterable<IDomNode> getRecursiveIterable() {

		return new RecurseIterable<>(Collections.singleton(rootNode), this::getChildren);
	}

	private Collection<IDomNode> getChildren(IDomNode node) {

		return CastUtils//
			.tryCast(node, IDomParentElement.class)
			.map(IDomParentElement::getChildren)
			.orElse(Collections.emptyList());
	}
}
