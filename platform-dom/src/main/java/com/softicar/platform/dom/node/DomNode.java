package com.softicar.platform.dom.node;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.elements.dialog.DomModalAlertPopup;
import com.softicar.platform.dom.elements.dialog.DomModalConfirmPopup;
import com.softicar.platform.dom.elements.dialog.DomModalPromptPopup;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Represents a tree node of an {@link IDomDocument}.
 *
 * @author Oliver Richers
 */
public abstract class DomNode extends AbstractDomNode {

	// -------------------- BASIC METHODS -------------------- //

	protected DomNode() {

		super(CurrentDomDocument.get());
	}

	protected DomNode(IDomDocument document) {

		super(document);
	}

	// -------------------------------- INTERACTION -------------------------------- //

	@Override
	public final void executeAlert(IDisplayString message) {

		new DomModalAlertPopup(message).show();
	}

	@Override
	public void executeConfirm(INullaryVoidFunction confirmHandler, IDisplayString message) {

		new DomModalConfirmPopup(confirmHandler, message).show();
	}

	@Override
	public void executePrompt(Consumer<String> promptHandler, IDisplayString message, String defaultValue) {

		new DomModalPromptPopup(promptHandler, message, defaultValue).show();
	}

	// -------------------------------- HTML -------------------------------- //

	protected void buildAttributesHTML(Appendable out) throws IOException {

		out.append(" id='n" + getNodeId() + "'");
		for (IDomAttribute attribute: getAttributes()) {
			out.append(" ");
			out.append(attribute.getName() + "=" + attribute.getValue_JS());
		}
	}

	/**
	 * Builds an HTML representation of this DOMNode.
	 *
	 * @param out
	 *            the object implementing {@link Appendable} for outputting the
	 *            HTML code
	 */
	@Override
	public abstract void buildHtml(Appendable out) throws IOException;
}
