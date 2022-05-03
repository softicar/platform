package com.softicar.platform.dom.input;

import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;

/**
 * Interface to focus or blur {@link IDomElement} instances.
 *
 * @author Oliver Richers
 */
public interface IDomFocusable extends IDomElement {

	/**
	 * Assigns or removes the input focus on this {@link IDomElement}.
	 * <p>
	 * Please note that an {@link IDomElement} can only gain the focus if it is
	 * appended to the {@link DomDocument}. For example, if this method is
	 * called on an {@link IDomElement} appended to a {@link DomPopup}, the
	 * {@link DomPopup#open()} method must be called before this method is
	 * called. Otherwise, the call has no effect.
	 *
	 * @param focus
	 *            <i>true</i> to focus; <i>false</i> to blur
	 */
	default IDomElement setFocus(boolean focus) {

		if (focus) {
			getDomEngine().focus(this);
		} else {
			getDomEngine().blur(this);
		}
		return this;
	}

	/**
	 * Assigns the focus to this {@link IDomElement}.
	 * <p>
	 * Convenience method for {@link #setFocus(boolean)}.
	 */
	default void focus() {

		setFocus(true);
	}

	/**
	 * Finds the first {@link IDomNode} with the given class, starting from (and
	 * including) the given root node, and focuses it.
	 *
	 * @param focusableClass
	 *            the focusable {@link IDomNode} class to find (never
	 *            <i>null</i>)
	 * @param rootNode
	 *            the {@link IDomNode} to start searching at (never <i>null</i>)
	 * @return <i>true</i> if a node was focused; <i>false</i> otherwise
	 */
	static boolean focusFirst(Class<? extends IDomFocusable> focusableClass, IDomNode rootNode) {

		if (focusableClass.isInstance(rootNode)) {
			((IDomFocusable) rootNode).focus();
			return true;
		} else if (rootNode instanceof IDomParentElement) {
			for (IDomNode child: ((IDomParentElement) rootNode).getChildren()) {
				if (focusFirst(focusableClass, child)) {
					return true;
				}
			}
		}
		return false;
	}
}
