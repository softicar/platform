package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.dom.elements.popup.DomPopup;

/**
 * A display mode for {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public interface IDomPopupDisplayMode {

	/**
	 * Determines whether a header is displayed.
	 *
	 * @return <i>true</i> if a header is displayed; <i>false</i> otherwise
	 */
	boolean hasHeader();

	/**
	 * Determines whether the {@link DomPopup} is maximized.
	 *
	 * @return <i>true</i> if the {@link DomPopup} is maximized; <i>false</i>
	 *         otherwise
	 */
	boolean isMaximized();

	/**
	 * Determines whether the {@link DomPopup} is displayed in a compact manner.
	 *
	 * @return <i>true</i> if the {@link DomPopup} is compact; <i>false</i>
	 *         otherwise
	 */
	boolean isCompact();

	/**
	 * Returns the {@link DomPopupModalMode} of the {@link DomPopup}.
	 *
	 * @return the {@link DomPopupModalMode} (never <i>null</i>)
	 */
	DomPopupModalMode getModalMode();

	/**
	 * Returns the default {@link DomPopupChildClosingMode} of the
	 * {@link DomPopup}.
	 *
	 * @return the default {@link DomPopupChildClosingMode} (never <i>null</i>)
	 */
	DomPopupChildClosingMode getDefaultChildClosingMode();
}
