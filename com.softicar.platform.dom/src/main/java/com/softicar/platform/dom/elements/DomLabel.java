package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.input.IDomInputNode;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.node.tree.DomNodeTree;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html label element.
 * <p>
 * A label element can be connected to an {@link IDomInputNode}. If the user
 * clicks on the label, the connected input node will be selected. If the input
 * node is a text input, the input will be focused. If the input is a check-box
 * or radio-button, the <i>checked</i> state of the input will be toggled.
 *
 * @author Oliver Richers
 */
public class DomLabel extends DomParentElement {

	private static final String FOR_ATTRIBUTE_NAME = "for";

	/**
	 * Constructs an empty label.
	 * <p>
	 * This label will not be connected to anything and will show no text. You
	 * should call {@link #setFor(IDomInputNode)} to connect this label to an
	 * input element, and call {@link #appendText(IDisplayString)} to define the
	 * text to display.
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
	 * Constructs an empty label connected to the given input element.
	 * <p>
	 * This label will show no text. You should call
	 * {@link #appendText(IDisplayString)} to define the text to display.
	 *
	 * @param inputNode
	 *            the input note to connect this label to
	 */
	public DomLabel(IDomInputNode inputNode) {

		setFor(inputNode);
	}

	/**
	 * Defines which input note this label points to.
	 *
	 * @param inputNode
	 *            the input note to connect this label to
	 * @return this
	 */
	public DomLabel setFor(IDomInputNode inputNode) {

		setAttribute(FOR_ATTRIBUTE_NAME, inputNode.getNodeIdString());
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
	 * {@link IDomInputNode}, {@link #setFor(IDomInputNode)} is called with the
	 * first {@link IDomInputNode} found.
	 * <p>
	 * If the given {@link IDomNode} is <i>null</i> or does not implement
	 * {@link IDomInputNode} and none of its children implement
	 * {@link IDomInputNode}, {@link #resetFor()} is called.
	 *
	 * @param node
	 *            the {@link IDomNode} (may be <i>null</i>)
	 * @return this
	 */
	public DomLabel updateFor(IDomNode node) {

		new DomNodeTree(node)//
			.stream(IDomInputNode.class)
			.findFirst()
			.ifPresentOrElse(this::setFor, this::resetFor);
		return this;
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.LABEL;
	}
}
