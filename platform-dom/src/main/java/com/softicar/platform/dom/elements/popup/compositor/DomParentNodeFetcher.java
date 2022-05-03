package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.Objects;
import java.util.Optional;

/**
 * Facilitates finding parents of a specific type for a given {@link IDomNode}.
 *
 * @author Alexander Schmidt
 */
class DomParentNodeFetcher<T extends IDomNode> {

	private final Class<T> parentNodeClass;

	/**
	 * Constructs a new {@link DomParentNodeFetcher}.
	 *
	 * @param parentNodeClass
	 *            the expected parent {@link IDomNode} class (never <i>null</i>)
	 */
	public DomParentNodeFetcher(Class<T> parentNodeClass) {

		this.parentNodeClass = Objects.requireNonNull(parentNodeClass);
	}

	/**
	 * Determines the closest transitive parent of the specified class for the
	 * given {@link IDomNode}.
	 * <p>
	 * Returns {@link Optional#empty()} if the given {@link IDomNode} has no
	 * such transitive parent.
	 *
	 * @param node
	 *            the {@link IDomNode} to search a parent for (never
	 *            <i>null</i>)
	 * @return the closest transitive parent of the specified class
	 */
	public Optional<T> getClosestParent(IDomNode node) {

		Objects.requireNonNull(node);
		Objects.requireNonNull(parentNodeClass);

		IDomNode child = node;
		IDomParentElement parent = null;
		do {
			parent = child.getParent();
			child = parent;
		} while (parent != null && !parentNodeClass.isInstance(parent));
		return Optional.ofNullable(parentNodeClass.cast(parent));
	}
}
