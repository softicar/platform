package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.dialog.DomModalDialog;

class DomPopupCompositorChildClosingDialogPopup extends DomModalDialog {

	public DomPopupCompositorChildClosingDialogPopup() {

		appendContent(DomI18n.ARE_YOU_SURE_TO_CLOSE_THIS_WINDOW_AND_ALL_SUB_WINDOWS_QUESTION);
	}

	public void appendCloseOnlyThisButton(INullaryVoidFunction callback) {

		appendActionButton(//
			DomElementsImages.DIALOG_OKAY.getResource(),
			DomI18n.CLOSE_ONLY_THIS,
			callback,
			DomPopupCompositorDialogMarker.BUTTON_CLOSE_PARENT_ONLY);
	}

	public void appendCloseAllButton(INullaryVoidFunction callback) {

		appendActionButton(//
			DomElementsImages.DIALOG_OKAY_ALL.getResource(),
			DomI18n.CLOSE_ALL,
			callback,
			DomPopupCompositorDialogMarker.BUTTON_CLOSE_ALL);
	}

	public void appendCancelButton(INullaryVoidFunction callback) {

		appendActionButton(//
			DomElementsImages.DIALOG_CANCEL.getResource(),
			DomI18n.CANCEL,
			callback,
			DomPopupCompositorDialogMarker.BUTTON_CANCEL);
	}
}
