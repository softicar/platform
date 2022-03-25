package com.softicar.platform.dom.elements.input.diagnostics;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;

/**
 * An {@link DomInputDiagnosticsDisplay} around an input node.
 * <p>
 * This {@link DomInputDiagnosticsFrame} styles an input node according to the
 * {@link DomInputDiagnosticsState}. It also provides the possibility to show
 * diagnostic messages.
 *
 * @author Oliver Richers
 */
public class DomInputDiagnosticsFrame extends DomInputDiagnosticsDisplay {

	private DomDiv messageDiv;

	public DomInputDiagnosticsFrame() {

		this.messageDiv = null;
	}

	/**
	 * Clears the {@link DomInputDiagnosticsState} and removes all messages.
	 */
	public void clear() {

		clearDiagnosticsState();
		removeMessageDivIfExisting();
	}

	/**
	 * Adds the given message above the input node.
	 *
	 * @param message
	 *            the message to append (never <i>null</i>)
	 * @return this
	 */
	public DomInputDiagnosticsFrame addMessage(IDisplayString message) {

		addMessageDivIfMissing();

		messageDiv.appendText(message.toString());
		messageDiv.appendNewChild(DomElementTag.BR);

		return this;
	}

	private void addMessageDivIfMissing() {

		if (messageDiv == null) {
			this.messageDiv = appendChild(new DomDiv());
		}
	}

	private void removeMessageDivIfExisting() {

		if (messageDiv != null) {
			this.messageDiv.disappend();
			this.messageDiv = null;
		}
	}
}
