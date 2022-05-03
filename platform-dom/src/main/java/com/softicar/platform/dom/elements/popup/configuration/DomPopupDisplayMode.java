package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.compositor.IDomPopupMaximizationContext;

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
	DIALOG(false, false, true, DomPopupModalMode.MODAL_DISMISSABLE, DomPopupChildClosingMode.INTERACTIVE_ALL),

	/**
	 * A non-modal, draggable {@link DomPopup} with header (captions and
	 * close-button).
	 */
	DRAGGABLE(true, false, true, DomPopupModalMode.NON_MODAL, DomPopupChildClosingMode.AUTOMATIC_NONE),

	/**
	 * A modal, dismissable, draggable {@link DomPopup} with header (captions
	 * and close-button). Provides a visual clue to its modality.
	 */
	DRAGGABLE_MODAL(true, false, true, DomPopupModalMode.MODAL_DISMISSABLE, DomPopupChildClosingMode.INTERACTIVE_ALL),

	/**
	 * A {@link DomPopup} with header (captions and close-button) that occupies
	 * all available space in the hierarchically closest
	 * {@link IDomPopupMaximizationContext}.
	 */
	MAXIMIZED(true, true, true, DomPopupModalMode.NON_MODAL, DomPopupChildClosingMode.INTERACTIVE_ALL),

	/**
	 * A modal, dismissable, non-draggable {@link DomPopup} without header.
	 * Provides <b>no</b> visual clue to its modality.
	 */
	POPOVER(false, false, false, DomPopupModalMode.MODAL_DISMISSABLE_INVISIBLE_BACKDROP, DomPopupChildClosingMode.AUTOMATIC_NONE);

	private final boolean header;
	private final boolean maximized;
	private final boolean focusFirst;
	private final DomPopupModalMode modalMode;
	private final DomPopupChildClosingMode defaultChildClosingMode;

	private DomPopupDisplayMode(boolean header, boolean maximized, boolean focusFirst, DomPopupModalMode modalMode,
			DomPopupChildClosingMode defaultChildClosingMode) {

		this.header = header;
		this.maximized = maximized;
		this.focusFirst = focusFirst;
		this.modalMode = modalMode;
		this.defaultChildClosingMode = defaultChildClosingMode;
	}

	public boolean hasHeader() {

		return header;
	}

	public boolean isMaximized() {

		return maximized;
	}

	public boolean isFocusFirst() {

		return focusFirst;
	}

	public DomPopupModalMode getModalMode() {

		return modalMode;
	}

	public DomPopupChildClosingMode getDefaultChildClosingMode() {

		return defaultChildClosingMode;
	}
}
