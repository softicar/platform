package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;

class DomPopupFrameCloseButton extends DomButton {

	public DomPopupFrameCloseButton(INullaryVoidFunction closeCallback) {

		setIcon(DomElementsImages.POPUP_TITLE_BAR_CLOSE.getResource());
		setTitle(DomI18n.CLOSE);
		setClickCallback(closeCallback);
		addMarker(DomPopupMarker.FRAME_CLOSE_BUTTON);
		CurrentDomDocument.get().getEngine().stopPropagation(this, "mousedown");
	}
}
