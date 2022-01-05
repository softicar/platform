package com.softicar.platform.dom.elements.button.popup;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.function.Supplier;

public class DomPopupButton extends DomButton {

	private DomPopup popup;
	private INullaryVoidFunction callbackBeforeShow = INullaryVoidFunction.NO_OPERATION;
	private INullaryVoidFunction callbackAfterShow = INullaryVoidFunction.NO_OPERATION;

	public DomPopupButton setPopupFactory(Supplier<DomPopup> popupFactory) {

		setClickCallback(() -> showPopup(popupFactory));
		return this;
	}

	public DomPopupButton setCallbackBeforeShow(INullaryVoidFunction callback) {

		this.callbackBeforeShow = callback;
		return this;
	}

	public DomPopupButton setCallbackAfterShow(INullaryVoidFunction callback) {

		this.callbackAfterShow = callback;
		return this;
	}

	private void showPopup(Supplier<DomPopup> popupFactory) {

		if (popup != null) {
			popup.hide();
			popup = null;
		}
		callbackBeforeShow.apply();
		popup = popupFactory.get();
		popup.show();

		callbackAfterShow.apply();
	}
}
