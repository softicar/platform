package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.dom.elements.popup.DomPopup;

/**
 * Enumerates possible display modes of a {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public enum DomPopupDisplayMode {

	/**
	 * A modal, dismissable, non-draggable {@link DomPopup} without header.
	 * Provides a visual clue to its modality.
	 */
	DIALOG(false, DomPopupModalMode.MODAL_DISMISSABLE),

	/**
	 * A non-modal, draggable {@link DomPopup} with header (captions and
	 * close-button).
	 */
	DRAGGABLE(true, DomPopupModalMode.NON_MODAL),

	/**
	 * A modal, dismissable, draggable {@link DomPopup} with header (captions
	 * and close-button). Provides a visual clue to its modality.
	 */
	DRAGGABLE_MODAL(true, DomPopupModalMode.MODAL_DISMISSABLE),

	/**
	 * A modal, dismissable, non-draggable {@link DomPopup} without header.
	 * Provides <b>no</b> visual clue to its modality.
	 */
	POPOVER(false, DomPopupModalMode.MODAL_DISMISSABLE_INVISIBLE_BACKDROP);

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
}
