package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomTestMarker;

/**
 * A custom modal dialog that replaces a native "alert" dialog.
 *
 * @author Alexander Schmidt
 */
public class DomModalAlertDialog extends DomModalDialog {

	/**
	 * Constructs a new {@link DomModalAlertDialog} that displays the given
	 * message, and a "Close" button.
	 *
	 * @param message
	 *            the message to display (never <i>null</i>)
	 */
	public DomModalAlertDialog(IDisplayString message) {

		appendContent(message);
		appendCloseButton().addMarker(DomTestMarker.MODAL_ALERT_CLOSE_BUTTON);
	}
}
