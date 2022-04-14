package com.softicar.platform.dom.elements.popup.modal;

import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.popup.DomPopup;

/**
 * A modal variant of {@link DomPopup} that is dismissed when the user clicks
 * elsewhere, or hits ESC.
 * <p>
 * In contrast to {@link DomPopup}, it does not have a header (i.e. captions),
 * and it generally looks more compact.
 *
 * @author Alexander Schmidt
 */
public class DomPopover extends DomDismissablePopup {

	public DomPopover() {

		addCssClass(DomElementsCssClasses.DOM_POPOVER);
		setDisplayHeader(false);
	}
}
