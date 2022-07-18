package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.node.IDomNode;
import java.util.Objects;
import java.util.Optional;

/**
 * Finds parents of a specific class for a given {@link IDomNode}.
 *
 * @author Alexander Schmidt
 */
public class DomParentNodeFinder<T extends IDomNode> {

	private final Class<T> parentClass;

	/**
	 * Constructs a new {@link DomParentNodeFinder}.
	 *
	 * @param parentClass
	 *            the expected parent {@link IDomNode} class (never <i>null</i>)
	 */
	public DomParentNodeFinder(Class<T> parentClass) {

		this.parentClass = Objects.requireNonNull(parentClass);
	}

	/**
	 * Same as {@link #findClosestParent(IDomNode, boolean)} but never includes
	 * the given child {@link IDomNode} itself.
	 */
	public Optional<T> findClosestParent(IDomNode child) {

		return findClosestParent(child, false);
	}

	/**
	 * For the given child {@link IDomNode}, finds the closest transitive parent
	 * of the type given to {@link #DomParentNodeFinder(Class)}.
	 * <p>
	 * Returns {@link Optional#empty()} if the given {@link IDomNode} has no
	 * such transitive parent.
	 * <p>
	 * Returns the given {@link IDomNode} itself if {@code includeChild} is
	 * <i>true</i> and the given {@link IDomNode} is of the specified class.
	 *
	 * @param child
	 *            the {@link IDomNode} to search a parent for (never
	 *            <i>null</i>)
	 * @return the closest transitive parent {@link IDomNode} that matches the
	 *         specified class
	 */
	public Optional<T> findClosestParent(IDomNode child, boolean includeChild) {

		Objects.requireNonNull(child);
		IDomNode node = includeChild? child : child.getParent();
		while (node != null) {
			if (parentClass.isInstance(node)) {
				return Optional.of(parentClass.cast(node));
			}
			node = node.getParent();
		}
		return Optional.empty();
	}

	/**
	 * For the given child {@link IDomNode}, finds the most distant transitive
	 * parent of the type given to {@link #DomParentNodeFinder(Class)}.
	 * <p>
	 * Returns {@link Optional#empty()} if the given {@link IDomNode} has no
	 * such transitive parent, or no parent at all.
	 *
	 * @param child
	 *            the {@link IDomNode} to search a parent for (never
	 *            <i>null</i>)
	 * @return the most distant transitive parent {@link IDomNode} that matches
	 *         the specified class
	 */
	public Optional<T> findMostDistantParent(IDomNode child) {

		Objects.requireNonNull(child);
		IDomNode parent = null;
		T typedParent = null;
		do {
			parent = child.getParent();
			if (parentClass.isInstance(parent)) {
				typedParent = parentClass.cast(parent);
			}
			child = parent;
		} while (parent != null);
		return Optional.ofNullable(typedParent);
	}
}
