package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.input.IDomFocusable;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.node.tree.DomNodeTree;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an HTML label element.
 * <p>
 * A label element can be connected to an {@link IDomFocusable}. If the user
 * clicks on the label, the connected {@link IDomFocusable} will be focused.
 *
 * @author Oliver Richers
 */
public class DomLabel extends DomParentElement {

	protected static final String FOR_ATTRIBUTE_NAME = "for";

	/**
	 * Constructs an empty label.
	 * <p>
	 * This label will not be connected to anything and will show no text. You
	 * should call {@link #setFor} to connect this label to an
	 * {@link IDomFocusable}, and call {@link #appendText(IDisplayString)} to
	 * define the text to display.
	 */
	public DomLabel() {

		// nothing to do
	}

	/**
	 * Constructs a new label with the given text.
	 *
	 * @param labelString
	 *            the label text to append (never <i>null</i>)
	 */
	public DomLabel(IDisplayString labelString) {

		appendText(labelString);
	}

	/**
	 * Constructs an empty label connected to the given {@link IDomFocusable}.
	 * <p>
	 * This label will show no text. You should call
	 * {@link #appendText(IDisplayString)} to define the text to display.
	 *
	 * @param target
	 *            the {@link IDomFocusable} to connect this label to
	 */
	public DomLabel(IDomFocusable target) {

		setFor(target);
	}

	/**
	 * Defines which {@link IDomFocusable} this label points to.
	 *
	 * @param target
	 *            the {@link IDomFocusable} to connect this label to
	 * @return this
	 */
	public DomLabel setFor(IDomFocusable target) {

		setAttribute(FOR_ATTRIBUTE_NAME, target.getNodeIdString());
		return this;
	}

	/**
	 * Clears the <i>for</i> attribute of this {@link DomLabel}.
	 *
	 * @return this
	 */
	public DomLabel resetFor() {

		unsetAttribute(FOR_ATTRIBUTE_NAME);
		return this;
	}

	/**
	 * Updates the <i>for</i> attribute of this {@link DomLabel} according to
	 * the given {@link IDomNode}.
	 * <p>
	 * If the given {@link IDomNode} or one of its children implements
	 * {@link IDomFocusable}, {@link #setFor} is called with the first
	 * {@link IDomFocusable} found.
	 * <p>
	 * If the given {@link IDomNode} is <i>null</i> or does not implement
	 * {@link IDomFocusable} and none of its children implement
	 * {@link IDomFocusable}, {@link #resetFor()} is called.
	 *
	 * @param node
	 *            the {@link IDomNode} (may be <i>null</i>)
	 * @return this
	 */
	public DomLabel updateFor(IDomNode node) {

		new DomNodeTree(node)//
			.stream(IDomFocusable.class)
			.findFirst()
			.ifPresentOrElse(this::setFor, this::resetFor);
		return this;
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.LABEL;
	}
}
