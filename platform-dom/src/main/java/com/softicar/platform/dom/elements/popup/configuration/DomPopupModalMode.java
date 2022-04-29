package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.dom.elements.popup.DomPopup;

/**
 * Enumerates possible modes of modality of a {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public enum DomPopupModalMode {

	/**
	 * A modal {@link DomPopup} with a backdrop. The backdrop can be clicked to
	 * dismiss the {@link DomPopup}.
	 */
	MODAL_DISMISSABLE(true),

	/**
	 * A modal {@link DomPopup} with a backdrop. The backdrop can <b>not</b> be
	 * clicked to dismiss the {@link DomPopup}.
	 */
	MODAL_NON_DISMISSABLE(true),

	/**
	 * A non-modal {@link DomPopup}.
	 */
	NON_MODAL(false);

	private final boolean modal;

	private DomPopupModalMode(boolean modal) {

		this.modal = modal;
	}

	/**
	 * Determines whether this mode is in fact modal, or a dummy.
	 *
	 * @return <i>true</i> if this is a modal mode; <i>false</i> otherwise
	 */
	public boolean isModal() {

		return modal;
	}
}
