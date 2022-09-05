package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.button.DomButton;

class DomPopupFrameCloseButton extends DomButton {

	public DomPopupFrameCloseButton(INullaryVoidFunction closeCallback) {

		setIcon(DomImages.POPUP_TITLE_BAR_CLOSE.getResource());
		setTitle(DomI18n.CLOSE);
		setClickCallback(closeCallback);
		addMarker(DomTestMarker.POPUP_FRAME_CLOSE_BUTTON);
		CurrentDomDocument.get().getEngine().stopPropagation(this, "mousedown");
	}
}
