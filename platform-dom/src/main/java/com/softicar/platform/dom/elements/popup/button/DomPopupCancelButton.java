package com.softicar.platform.dom.elements.popup.button;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.popup.DomPopup;

public class DomPopupCancelButton extends DomPopupCloseCancelButton {

	public DomPopupCancelButton(DomPopup popup) {

		super(popup, DomImages.DIALOG_CANCEL.getResource(), DomI18n.CANCEL);
	}
}
