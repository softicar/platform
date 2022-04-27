package com.softicar.platform.dom.parent;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.text.IDomTextNode;
import java.util.List;
import java.util.Optional;

/**
 * Interface of all parent elements.
 *
 * @author Oliver Richers
 */
public interface IDomParentElement extends IDomElement {

	// -------------------------------- BASIC TREE METHODS -------------------------------- //

	/**
	 * Inserts the child node at the specified index.
	 * <p>
	 * The child node must not have a parent node when calling this function.
	 * The index must be in the range [0,N] where N is the current number of
	 * child nodes.
	 *
	 * @param <T>
	 *            the type of the child node
	 * @param child
	 *            the child node to add
	 * @param index
	 *            the position where to insert the child node
	 * @return the inserted child node
	 */
	<T extends IDomNode> T insertAt(T child, int index);

	/**
	 * Replaces an old child node with a new child node.
	 *
	 * @param <T>
	 *            the type of the new child node
	 * @param newChild
	 *            the new child note to replace the old child node
	 * @param oldChild
	 *            the old child node to be replaced by the new child node
	 * @return the new child node
	 */
	<T extends IDomNode> T replaceChild(T newChild, IDomNode oldChild);

	/**
	 * Removes a child node from this block element.
	 *
	 * @param child
	 *            the child node to be removed
	 */
	void removeChild(IDomNode child);

	/**
	 * Removes all child nodes from this block element.
	 */
	void removeChildren();

	// -------------------------------- CONVENIENCE TREE METHODS -------------------------------- //

	/**
	 * Appends the child node at the end of the list of child nodes.
	 * <p>
	 * This is a convenience method, using {@link #insertAt(IDomNode, int)} with
	 * an index of {@link #getChildCount()}.
	 *
	 * @param <T>
	 *            the type of the child node
	 * @param child
	 *            the child node to append
	 * @return the appended child node
	 */
	<T extends IDomNode> T appendChild(T child);

	/**
	 * Convenience method to append a child of arbitrary type.
	 * <p>
	 * This will call {@link #appendText(String)} if the specified object is of
	 * type {@link String}. If it is of type {@link IDomNode}, this will call
	 * {@link #appendChild(IDomNode)}. If the specified object is neither
	 * {@link String} nor {@link IDomNode}, it will be converted using
	 * {@link Object#toString()} and appended using {@link #appendText(String)}.
	 *
	 * @param child
	 */
	void appendChild(Object child);

	/**
	 * Appends the specified list of children using the method
	 * {@link #appendChild(Object)} in the given order.
	 *
	 * @param children
	 *            the children to append
	 */
	void appendChildren(Object...children);

	/**
	 * This is a convenience method, using {@link #insertAt(IDomNode, int)} with
	 * an index of zero.
	 *
	 * @param <T>
	 *            the type of the child
	 * @param child
	 *            the child to be prepended
	 * @return the prepended child
	 */
	<T extends IDomNode> T prependChild(T child);

	/**
	 * Inserts the child node before the other specified child node.
	 * <p>
	 * This is a convenience method, using {@link #insertAt(IDomNode, int)}. The
	 * insert index is determined using {@link #getChildIndex(IDomNode)}.
	 *
	 * @param <T>
	 *            the type of the child node
	 * @param child
	 *            the child node to insert
	 * @param otherChild
	 *            the child node before which to do the insert
	 * @return the inserted child node
	 */
	<T extends IDomNode> T insertBefore(T child, IDomNode otherChild);

	/**
	 * Inserts the child node after the other specified child node.
	 * <p>
	 * This is a convenience method, using {@link #insertAt(IDomNode, int)}. The
	 * insert index is determined using {@link #getChildIndex(IDomNode)}.
	 *
	 * @param <T>
	 *            the type of the child node
	 * @param child
	 *            the child node to insert
	 * @param otherChild
	 *            the child node after which to do the insert
	 * @return the inserted child node
	 */
	<T extends IDomNode> T insertAfter(T child, IDomNode otherChild);

	/**
	 * Removes all children appended after the given child node.
	 *
	 * @param child
	 *            the marker child (will not be removed)
	 */
	void removeChildrenAfter(IDomNode child);

	/**
	 * Returns whether the given node is a child of this node.
	 *
	 * @param child
	 *            the node to test
	 * @return true if the given node is a child of this node, false otherwise
	 */
	boolean hasChild(IDomNode child);

	/**
	 * Appends a new text node to this element.
	 * <p>
	 * This is a convenience method, using {@link #appendChild(IDomNode)}.
	 *
	 * @param text
	 *            the text of the appended text node
	 * @return the appended text node
	 */
	IDomTextNode appendText(String text);

	/**
	 * Appends a new text node to this element.
	 *
	 * @param text
	 *            the text of the appended text node
	 * @return the appended text node
	 */
	default IDomTextNode appendText(IDisplayString text) {

		return appendText(Optional.ofNullable(text).map(IDisplayString::toString).orElse(null));
	}

	/**
	 * Adds a new text node as the first child of this element.
	 *
	 * @param text
	 *            the text of the prepended text node
	 * @return the prepended text node
	 */
	IDomTextNode prependText(String text);

	/**
	 * Adds a new text node as the first child of this element.
	 *
	 * @param text
	 *            the text of the prepended text node
	 * @return the prepended text node
	 */
	default IDomTextNode prependText(IDisplayString text) {

		return prependText(Optional.ofNullable(text).map(IDisplayString::toString).orElse(null));
	}

	/**
	 * Appends a new text node to this block element using the String.format
	 * function.
	 * <p>
	 * This is a convenience method, using {@link #prependText(String)}.
	 *
	 * @param text
	 *            the text of the appended text node
	 * @param args
	 *            the arguments to the String.format function
	 * @return the appended text node
	 */
	IDomTextNode appendText(String text, Object...args);

	/**
	 * Appends a new child element to this block element.
	 * <p>
	 * This is just a convenience method to quickly add a line break or
	 * horizontal line.
	 *
	 * @param tag
	 *            the HTML tag of the child element
	 * @return the newly created child element
	 */
	IDomParentElement appendNewChild(DomElementTag tag);

	// -------------------------------- NON-MUTATING TREE METHODS -------------------------------- //

	/**
	 * Returns the number of child nodes.
	 */
	int getChildCount();

	/**
	 * Returns the index of the specified child node.
	 *
	 * @param child
	 *            the child node
	 * @return the index of the child node
	 */
	int getChildIndex(IDomNode child);

	/**
	 * Returns the child node with the specified index
	 *
	 * @param index
	 *            the index of the child node
	 * @return the child node with the specified index
	 */
	IDomNode getChild(int index);

	/**
	 * Returns a collection with all child nodes.
	 */
	List<IDomNode> getChildren();
}
