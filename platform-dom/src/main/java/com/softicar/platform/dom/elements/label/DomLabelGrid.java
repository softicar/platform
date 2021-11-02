package com.softicar.platform.dom.elements.label;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomLabel;
import com.softicar.platform.dom.input.IDomInputNode;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.text.DomTextNode;
import com.softicar.platform.dom.text.IDomTextNode;

/**
 * A grid of label and value nodes.
 *
 * @author Oliver Richers
 */
public class DomLabelGrid extends DomDiv {

	public DomLabelGrid() {

		addCssClass(DomElementsCssClasses.DOM_LABEL_GRID);
	}

	/**
	 * Appends a new {@link DomLabel} together with the given {@link IDomNode}.
	 * <p>
	 * If the given {@link IDomNode} or one of its children is an
	 * {@link IDomInputNode}, the <i>for</i> attribute of the {@link DomLabel}
	 * will be assigned respectively.
	 *
	 * @param labelText
	 *            the label text, without a colon (never <i>null</i>)
	 * @param node
	 *            the {@link IDomNode} (never <i>null</i>)
	 * @return this
	 */
	public DomLabelGrid add(IDisplayString labelText, IDomNode node) {

		appendChild(new DomLabel(labelText).updateFor(node));
		appendChild(node);
		return this;
	}

	/**
	 * Appends a new {@link DomLabel} together with the given value text as
	 * {@link IDomTextNode}.
	 *
	 * @param labelText
	 *            the label text, without a colon (never <i>null</i>)
	 * @param valueText
	 *            the text (never <i>null</i>)
	 * @return this
	 */
	public DomLabelGrid add(IDisplayString labelText, IDisplayString valueText) {

		return add(labelText, DomTextNode.create(valueText));
	}

	/**
	 * Appends a new {@link DomLabel} together with the given value text as
	 * {@link IDomTextNode}.
	 *
	 * @param labelText
	 *            the label text, without a colon (never <i>null</i>)
	 * @param valueText
	 *            the text (never <i>null</i>)
	 * @return this
	 */
	public DomLabelGrid add(IDisplayString labelText, String valueText) {

		return add(labelText, DomTextNode.create(valueText));
	}
}
