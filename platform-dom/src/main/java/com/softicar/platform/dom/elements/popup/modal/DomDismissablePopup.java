package com.softicar.platform.dom.elements.popup.modal;

import com.softicar.platform.dom.elements.popup.DomPopup;

/**
 * A modal variant of {@link DomPopup} that is dismissed when the user clicks
 * elsewhere, or hits ESC.
 * <p>
 * The modal behavior is indicated via a semi-transparent backdrop.
 * <p>
 * Beyond that, it looks just like a regular {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public class DomDismissablePopup extends DomPopup {

	public DomDismissablePopup() {

		configuration.setDisplayModeDraggableModal();
	}
}
