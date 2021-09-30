package com.softicar.platform.dom.elements.popup.button;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.popup.DomPopup;

public class DomPopupCloseButton extends DomPopupCloseCancelButton {

	public DomPopupCloseButton(DomPopup popup) {

		super(popup, DomElementsImages.DIALOG_CLOSE.getResource(), DomI18n.CLOSE);
	}
}
