package com.softicar.platform.dom.elements.popup.button;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.compositor.CurrentDomPopupCompositor;

class DomPopupCloseCancelButton extends DomButton {

	public DomPopupCloseCancelButton(DomPopup popup, IResource icon, IDisplayString label) {

		setIcon(icon);
		setLabel(label);
		setClickCallback(() -> CurrentDomPopupCompositor.get().closeInteractively(popup));
	}
}
