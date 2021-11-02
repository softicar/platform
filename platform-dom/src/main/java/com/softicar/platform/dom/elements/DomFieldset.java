package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * A border box for visual grouping of text and other elements as well.
 *
 * @author Boris Schaa
 * @author Oliver Richers
 */
public class DomFieldset extends DomParentElement {

	protected DomLegend legend;

	/**
	 * Constructs a {@link DomFieldset} without legend.
	 * <p>
	 * A legend may be added later by {@link #setLegend(IDisplayString)}.
	 */
	public DomFieldset() {

		// nothing to do
	}

	/**
	 * Constructs a {@link DomFieldset} with the specified legend text.
	 *
	 * @param legend
	 *            the {@link IDisplayString} to use as legend
	 */
	public DomFieldset(IDisplayString legend) {

		this.legend = appendChild(new DomLegend(legend));
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.FIELDSET;
	}

	public void setLegend(IDisplayString legendText) {

		if (legend == null) {
			legend = prependChild(new DomLegend(legendText));
		} else {
			legend.setLegend(legendText);
		}
	}
}
