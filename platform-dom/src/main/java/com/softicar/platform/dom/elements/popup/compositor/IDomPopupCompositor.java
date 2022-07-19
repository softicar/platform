package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.configuration.IDomPopupConfiguration;
import com.softicar.platform.dom.elements.popup.modal.DomModalPopupBackdrop;
import com.softicar.platform.dom.input.IDomTextualInput;

/**
 * A compositor that handles the visualization of {@link DomPopup} elements in
 * the {@link CurrentDomDocument}.
 *
 * @author Alexander Schmidt
 */
public interface IDomPopupCompositor {

	/**
	 * Opens the given {@link DomPopup}.
	 * <p>
	 * If the {@link DomPopup} is already open, it will be re-positioned, raised
	 * and focused.
	 *
	 * @param popup
	 *            the {@link DomPopup} to open (never <i>null</i>)
	 */
	void open(DomPopup popup);

	/**
	 * Closes the given {@link DomPopup}.
	 * <p>
	 * The user will <b>not</b> be prompted if closing the {@link DomPopup}
	 * requires interaction, according to the {@link IDomPopupConfiguration}.
	 * <p>
	 * If the {@link DomPopup} is not open, nothing will happen.
	 *
	 * @param popup
	 *            the {@link DomPopup} to close (never <i>null</i>)
	 */
	void close(DomPopup popup);

	/**
	 * Calls {@link #close(DomPopup)} on all open {@link DomPopup} elements.
	 * <p>
	 * Also resets the internal state of the {@link IDomPopupCompositor}. After
	 * this method was called, the {@link IDomPopupCompositor} no longer holds
	 * strong references to any {@link DomPopup}, {@link DomPopupFrame},
	 * {@link DomModalPopupBackdrop}, or related elements.
	 */
	void closeAll();

	/**
	 * Closes the given {@link DomPopup}.
	 * <p>
	 * The user will be prompted if closing the {@link DomPopup} requires
	 * interaction, according to the {@link IDomPopupConfiguration}.
	 * <p>
	 * If the {@link DomPopup} is not open, nothing will happen.
	 *
	 * @param popup
	 *            the {@link DomPopup} to close (never <i>null</i>)
	 */
	void closeInteractively(DomPopup popup);

	/**
	 * Focuses the first {@link IDomTextualInput} in the given {@link DomPopup}.
	 * <p>
	 * If no such element is found, the {@link DomPopupFrame} is focused.
	 * <p>
	 * If the {@link DomPopup} is not open, nothing will happen.
	 *
	 * @param popup
	 *            the {@link DomPopup} to focus (never <i>null</i>)
	 */
	void focus(DomPopup popup);

	/**
	 * Refreshes the {@link DomPopupFrame} of the given {@link DomPopup}.
	 * <p>
	 * Does <b>not</b> refresh the content of the {@link DomPopup}.
	 *
	 * @param popup
	 *            the {@link DomPopup} with the {@link DomPopupFrame} to refresh
	 *            (never <i>null</i>)
	 */
	void refreshFrame(DomPopup popup);

	/**
	 * Returns the current {@link IDomPopupHierarchyTree}.
	 *
	 * @return the current {@link IDomPopupHierarchyTree} (never <i>null</i>)
	 */
	IDomPopupHierarchyTree getHierarchyTree();
}
