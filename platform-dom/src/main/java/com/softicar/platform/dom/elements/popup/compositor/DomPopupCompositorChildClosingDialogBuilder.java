package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.dialog.DomModalDialogBuilder;
import java.util.Objects;

class DomPopupCompositorChildClosingDialogBuilder extends DomModalDialogBuilder {

	public DomPopupCompositorChildClosingDialogBuilder() {

		setMessage(DomI18n.ARE_YOU_SURE_TO_CLOSE_THIS_WINDOW_AND_ALL_SUB_WINDOWS_QUESTION);
	}

	public void addCloseParentOnlyOption(INullaryVoidFunction callback) {

		Objects.requireNonNull(callback);
		addOption(//
			DomElementsImages.DIALOG_OKAY.getResource(),
			DomI18n.CLOSE_THIS,
			callback,
			DomPopupCompositorDialogMarker.BUTTON_CLOSE_PARENT_ONLY);
	}

	void addCloseAllOption(INullaryVoidFunction callback) {

		Objects.requireNonNull(callback);
		addOption(//
			DomElementsImages.DIALOG_OKAY_ALL.getResource(),
			DomI18n.CLOSE_ALL,
			callback,
			DomPopupCompositorDialogMarker.BUTTON_CLOSE_ALL);
	}

	void addCancelOption(INullaryVoidFunction callback) {

		Objects.requireNonNull(callback);
		addOption(//
			DomElementsImages.DIALOG_CANCEL.getResource(),
			DomI18n.CANCEL,
			callback,
			DomPopupCompositorDialogMarker.BUTTON_CANCEL);
	}
}
