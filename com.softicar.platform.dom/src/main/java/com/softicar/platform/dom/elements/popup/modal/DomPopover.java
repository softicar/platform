package com.softicar.platform.dom.elements.popup.modal;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.dialog.DomModalDialogBackdrop;
import com.softicar.platform.dom.elements.popup.DomPopup;

public class DomPopover extends DomPopup {

	public DomPopover() {

		addCssClass(DomElementsCssClasses.DOM_POPOVER);
		setPositionByEvent();
		setDisplayHeader(false);
		setCallbackBeforeShow(this::beforeShow);
	}

	private void beforeShow() {

		showBackdrop(this::createBackdrop);
		trapTabFocus();
	}

	private DomModalDialogBackdrop createBackdrop() {

		DomModalDialogBackdrop backdrop = new DomModalDialogBackdrop(getCloseManager()::closePopupNonInteractive);
		backdrop.addCssClass(DomCssPseudoClasses.INVISIBLE);
		return backdrop;
	}
}
