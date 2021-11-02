package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.input.IDomFocusable;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an HTML anchor element.
 *
 * @author Oliver Richers
 */
public class DomAnchor extends DomParentElement implements IDomFocusable {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.A;
	}

	/**
	 * Defines the HTML hyper reference attribute of this {@link DomAnchor}.
	 *
	 * @param href
	 *            the HTML hyper reference (never <i>null</i>)
	 * @return this
	 */
	public DomAnchor setHRef(String href) {

		setAttribute("href", href);
		return this;
	}

	/**
	 * Sets the HTML reference of this anchor, so that the browser does not try
	 * to load the linked reference.
	 * <p>
	 * This method is useful if you want the anchor to execute JavaScript code
	 * defined by the <i>onclick</i> attribute of this anchor.
	 */
	public void setEmptyHRef() {

		setAttribute("href", "javascript:;");
	}

	/**
	 * Defines the HTML <i>target</i> attribute of this {@link DomAnchor} so
	 * that the hyper reference is opened in a new browser tab (or window).
	 *
	 * @param openInNewTab
	 *            <i>true</i> to open in a new tab; <i>false</i> to open in the
	 *            current tab
	 * @return this
	 */
	public DomAnchor setOpenInNewTab(boolean openInNewTab) {

		if (openInNewTab) {
			setAttribute("target", "_blank");
		} else {
			unsetAttribute("target");
		}
		return this;
	}
}
