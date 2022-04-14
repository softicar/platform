package com.softicar.platform.dom.elements.popup.modal;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.dialog.DomModalDialogBackdrop;
import com.softicar.platform.dom.elements.popup.DomPopup;

/**
 * A modal variant of {@link DomPopup} that is dismissed when the user clicks
 * elsewhere, or hits ESC.
 * <p>
 * Looks just like a regular {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public class DomDismissablePopup extends DomPopup {

	public DomDismissablePopup() {

		setPositionByEvent();
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
