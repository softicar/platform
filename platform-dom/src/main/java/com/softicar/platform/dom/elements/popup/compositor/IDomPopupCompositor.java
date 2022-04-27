package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.configuration.IDomPopupConfiguration;

/**
 * A compositor that handles the visualization of {@link DomPopup} elements in
 * the current IDomDocument.
 *
 * @author Alexander Schmidt
 */
public interface IDomPopupCompositor {

	/**
	 * Displays the given {@link DomPopup}.
	 * <p>
	 * If the {@link DomPopup} is already displayed, nothing will happen.
	 *
	 * @param popup
	 *            the {@link DomPopup} to show (never <i>null</i>)
	 */
	void show(DomPopup popup);

	/**
	 * Closes the given {@link DomPopup}.
	 * <p>
	 * If the {@link DomPopup} is not displayed, nothing will happen.
	 *
	 * @param popup
	 *            the {@link DomPopup} to show (never <i>null</i>)
	 */
	void close(DomPopup popup);

	/**
	 * Closes the given {@link DomPopup}.
	 * <p>
	 * The user will <b>not</b> be prompted for confirmation, even if
	 * {@link IDomPopupConfiguration#isConfirmBeforeClose()} is <i>true</i>.
	 * <p>
	 * If the {@link DomPopup} is not displayed, nothing will happen.
	 *
	 * @param popup
	 *            the {@link DomPopup} to show (never <i>null</i>)
	 */
	void closeWithoutConfirm(DomPopup popup);

	/**
	 * Focuses the first textual input element in the given {@link DomPopup}.
	 * <p>
	 * If no such element is found, the {@link DomPopupFrame} is focused.
	 * <p>
	 * If the {@link DomPopup} is not displayed, nothing will happen.
	 *
	 * @param popup
	 *            the {@link DomPopup} to focus (never <i>null</i>)
	 */
	void focus(DomPopup popup);

	/**
	 * Refreshes the {@link DomPopupFrame} of the given {@link DomPopup}.
	 *
	 * @param popup
	 *            the {@link DomPopup} with the {@link DomPopupFrame} to refresh
	 *            (never <i>null</i>)
	 */
	void refreshFrame(DomPopup popup);
}
