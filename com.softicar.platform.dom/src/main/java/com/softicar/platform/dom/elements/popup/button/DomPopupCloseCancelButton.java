package com.softicar.platform.dom.elements.popup.button;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;

class DomPopupCloseCancelButton extends DomButton {

	public DomPopupCloseCancelButton(DomPopup popup, IResource icon, IDisplayString label) {

		setIcon(icon);
		setLabel(label);
		setClickCallback(popup.getCloseManager()::closePopupInteractive);
	}
}
