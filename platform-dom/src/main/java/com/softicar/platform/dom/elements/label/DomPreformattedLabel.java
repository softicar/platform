package com.softicar.platform.dom.elements.label;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomCode;
import com.softicar.platform.dom.styles.CssWhiteSpace;

/**
 * Displays the given text in a {@link DomCode} retaining any whitespace.
 * <p>
 * White-space will be preserved using {@link CssWhiteSpace#PRE}.
 *
 * @author Oliver Richers
 */
public class DomPreformattedLabel extends DomCode {

	private String text = "";

	public DomPreformattedLabel() {

		addCssClass(DomCssClasses.DOM_PREFORMATTED_LABEL);
	}

	/**
	 * Creates a new label with the given text.
	 *
	 * @param text
	 *            the text to append
	 */
	public DomPreformattedLabel(String text) {

		this();
		setText(text);
	}

	public DomPreformattedLabel(IDisplayString text) {

		this();
		setText(text.toString());
	}

	public void setText(String text) {

		this.text = text;

		removeChildren();
		appendText(text);
	}

	public void setText(IDisplayString text) {

		setText(text.toString());
	}

	public void clear() {

		setText("");
	}

	@Override
	public String toString() {

		return text;
	}
}
