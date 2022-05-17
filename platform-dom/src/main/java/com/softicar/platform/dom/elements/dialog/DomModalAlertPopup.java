package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * A custom modal dialog that replaces a native "alert" dialog.
 *
 * @author Alexander Schmidt
 */
public class DomModalAlertPopup extends DomModalDialogPopup {

	/**
	 * Constructs a new {@link DomModalAlertPopup} that displays the given
	 * message, and a "Close" button.
	 *
	 * @param message
	 *            the message to display (never <i>null</i>)
	 */
	public DomModalAlertPopup(IDisplayString message) {

		appendContent(message);
		appendCloseButton().addMarker(DomModalAlertMarker.CLOSE_BUTTON);
	}
}
