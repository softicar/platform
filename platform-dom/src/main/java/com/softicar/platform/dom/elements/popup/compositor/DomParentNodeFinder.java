package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.node.IDomNode;
import java.util.Objects;
import java.util.Optional;

/**
 * Facilitates finding parents of a specific type for a given {@link IDomNode}.
 *
 * @author Alexander Schmidt
 */
class DomParentNodeFinder<T extends IDomNode> {

	private final Class<T> parentNodeClass;

	/**
	 * Constructs a new {@link DomParentNodeFinder}.
	 *
	 * @param nodeClass
	 *            the expected parent {@link IDomNode} class (never <i>null</i>)
	 */
	public DomParentNodeFinder(Class<T> nodeClass) {

		this.parentNodeClass = Objects.requireNonNull(nodeClass);
	}

	/**
	 * Finds the closest transitive parent of the specified class for the given
	 * {@link IDomNode}.
	 * <p>
	 * Returns {@link Optional#empty()} if the given {@link IDomNode} has no
	 * such transitive parent.
	 *
	 * @param root
	 *            the {@link IDomNode} to search a parent for (never
	 *            <i>null</i>)
	 * @return the closest transitive parent of the specified class
	 */
	public Optional<T> findClosestParent(IDomNode root) {

		return findClosestParent(root, false);
	}

	/**
	 * Finds the closest transitive parent of the specified class for the given
	 * {@link IDomNode}.
	 * <p>
	 * Returns {@link Optional#empty()} if the given {@link IDomNode} has no
	 * such transitive parent.
	 * <p>
	 * Returns the given {@link IDomNode} if {@code includeRoot} is <i>true</i>
	 * and the given node is of the specified class.
	 *
	 * @param root
	 *            the {@link IDomNode} to search a parent for (never
	 *            <i>null</i>)
	 * @return the closest transitive parent of the specified class
	 */
	public Optional<T> findClosestParent(IDomNode root, boolean includeRoot) {

		Objects.requireNonNull(root);
		IDomNode node = includeRoot? root : root.getParent();
		while (node != null) {
			if (parentNodeClass.isInstance(node)) {
				return Optional.ofNullable(parentNodeClass.cast(node));
			}
			node = node.getParent();
		}
		return Optional.empty();
	}
}
