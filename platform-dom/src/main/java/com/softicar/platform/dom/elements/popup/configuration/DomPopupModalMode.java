package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.dom.elements.popup.DomPopup;

/**
 * Enumerates possible modes of modality of a {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public enum DomPopupModalMode {

	/**
	 * A modal {@link DomPopup} with a visible backdrop. The backdrop can be
	 * clicked to dismiss the {@link DomPopup}.
	 */
	MODAL_DISMISSABLE(true, true, true),

	/**
	 * A modal {@link DomPopup} with an invisible backdrop. The backdrop can be
	 * clicked to dismiss the {@link DomPopup}.
	 */
	MODAL_DISMISSABLE_INVISIBLE_BACKDROP(true, true, false),

	/**
	 * A modal {@link DomPopup} with a visible backdrop. The backdrop can
	 * <b>not</b> be clicked to dismiss the {@link DomPopup}.
	 */
	MODAL_NON_DISMISSABLE(true, false, true),

	/**
	 * A non-modal {@link DomPopup}. Has no backdrop.
	 */
	NON_MODAL(false, false, false);

	private final boolean modal;
	private final boolean dismissable;
	private final boolean backdropVisible;

	private DomPopupModalMode(boolean modal, boolean dismissable, boolean backdropVisible) {

		this.modal = modal;
		this.dismissable = dismissable;
		this.backdropVisible = backdropVisible;
	}

	/**
	 * Determines whether this mode is in fact modal, or a dummy.
	 *
	 * @return <i>true</i> if this is a modal mode; <i>false</i> otherwise
	 */
	public boolean isModal() {

		return modal;
	}

	/**
	 * Determines whether the backdrop shall dismissable (i.e. disappear on
	 * click) if {@link #isModal()} is <i>true</i>.
	 *
	 * @return <i>true</i> if the backdrop shall be dismissable; <i>false</i>
	 *         otherwise
	 */
	public boolean isDismissable() {

		return dismissable;
	}

	/**
	 * Determines whether the backdrop shall be visible if {@link #isModal()} is
	 * <i>true</i>.
	 *
	 * @return <i>true</i> if the backdrop shall be visible; <i>false</i>
	 *         otherwise
	 */
	public boolean isBackdropVisible() {

		return backdropVisible;
	}
}
