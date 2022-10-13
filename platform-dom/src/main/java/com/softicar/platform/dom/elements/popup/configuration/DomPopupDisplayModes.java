package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.compositor.IDomPopupContext;

/**
 * Enumerates display modes of a {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public interface DomPopupDisplayModes {

	/**
	 * A modal, dismissable, non-draggable {@link DomPopup} without header.
	 * Provides a visual clue to its modality.
	 */
	IDomPopupDisplayMode DIALOG = new DomPopupDisplayMode()//
		.setHeader(false)
		.setMaximized(false)
		.setCompact(false)
		.setModalMode(DomPopupModalMode.MODAL_DISMISSABLE)
		.setDefaultChildClosingMode(DomPopupChildClosingMode.INTERACTIVE_ALL);

	/**
	 * A non-modal, draggable {@link DomPopup} with header (captions and
	 * close-button).
	 */
	IDomPopupDisplayMode DRAGGABLE = new DomPopupDisplayMode()//
		.setHeader(true)
		.setMaximized(false)
		.setCompact(false)
		.setModalMode(DomPopupModalMode.NON_MODAL)
		.setDefaultChildClosingMode(DomPopupChildClosingMode.AUTOMATIC_NONE);

	/**
	 * A modal, dismissable, draggable {@link DomPopup} with header (captions
	 * and close-button). Provides a visual clue to its modality.
	 */
	IDomPopupDisplayMode DRAGGABLE_MODAL = new DomPopupDisplayMode()//
		.setHeader(true)
		.setMaximized(false)
		.setCompact(false)
		.setModalMode(DomPopupModalMode.MODAL_DISMISSABLE)
		.setDefaultChildClosingMode(DomPopupChildClosingMode.INTERACTIVE_ALL);

	/**
	 * A {@link DomPopup} with header (captions and close-button) that occupies
	 * all available space in the hierarchically closest
	 * {@link IDomPopupContext}.
	 */
	IDomPopupDisplayMode MAXIMIZED = new DomPopupDisplayMode()//
		.setHeader(true)
		.setMaximized(true)
		.setCompact(false)
		.setModalMode(DomPopupModalMode.NON_MODAL)
		.setDefaultChildClosingMode(DomPopupChildClosingMode.INTERACTIVE_ALL);

	/**
	 * Same as {@link #MAXIMIZED} but without header.
	 */
	IDomPopupDisplayMode MAXIMIZED_NO_HEADER = new DomPopupDisplayMode()//
		.setHeader(false)
		.setMaximized(true)
		.setCompact(false)
		.setModalMode(DomPopupModalMode.NON_MODAL)
		.setDefaultChildClosingMode(DomPopupChildClosingMode.INTERACTIVE_ALL);

	/**
	 * A modal, dismissable, non-draggable {@link DomPopup} without header.
	 * Provides <b>no</b> visual clue to its modality.
	 */
	IDomPopupDisplayMode POPOVER = new DomPopupDisplayMode()//
		.setHeader(false)
		.setMaximized(false)
		.setCompact(true)
		.setModalMode(DomPopupModalMode.MODAL_DISMISSABLE_INVISIBLE_BACKDROP)
		.setDefaultChildClosingMode(DomPopupChildClosingMode.AUTOMATIC_NONE);
}
