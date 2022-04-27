package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.dom.elements.popup.DomPopup;

/**
 * Enumerates possible display modes of a {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public enum DomPopupDisplayMode {

	/**
	 * A modal, non-dismissable, non-draggable {@link DomPopup} without header.
	 */
	DIALOG(false, DomPopupModalMode.MODAL_DISMISSABLE),

	/**
	 * A non-modal, draggable {@link DomPopup} with header (captions and
	 * close-button).
	 */
	DRAGGABLE(true, DomPopupModalMode.NON_MODAL),

	/**
	 * A modal, dismissable, draggable {@link DomPopup} with header (captions
	 * and close-button).
	 */
	DRAGGABLE_MODAL(true, DomPopupModalMode.MODAL_DISMISSABLE);

	private final boolean header;
	private final DomPopupModalMode modalMode;

	private DomPopupDisplayMode(boolean header, DomPopupModalMode modalMode) {

		this.header = header;
		this.modalMode = modalMode;
	}

	public boolean hasHeader() {

		return header;
	}

	public DomPopupModalMode getModalMode() {

		return modalMode;
	}

	public boolean isModal() {

		return modalMode.isModal();
	}
}
