package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;

/**
 * Deals with closing a {@link DomPopup}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomPopupCloseManager {

	private final DomPopup popup;
	private final INullaryVoidFunction hideCallback;
	private boolean confirmBeforeClose = false;
	private boolean removeChildrenOnClose = false;
	private INullaryVoidFunction closeCallback = null;

	public DomPopupCloseManager(DomPopup popup, INullaryVoidFunction hideCallback) {

		this.popup = popup;
		this.hideCallback = hideCallback;
	}

	/**
	 * Defines if a confirmation is required before the popup will be closed.
	 *
	 * @param confirmBeforeClose
	 *            true to enable confirmation, false otherwise
	 */
	public void setConfirmBeforeClose(boolean confirmBeforeClose) {

		this.confirmBeforeClose = confirmBeforeClose;
	}

	/**
	 * Defines if all children of the popup shall be removed when the popup is
	 * closed.
	 *
	 * @param removeChildrenOnClose
	 *            true to remove all direct children, false otherwise
	 */
	public void setRemoveChildrenOnClose(boolean removeChildrenOnClose) {

		this.removeChildrenOnClose = removeChildrenOnClose;
	}

	/**
	 * Defines a callback to be called when the popup is closed.
	 *
	 * @param closeCallback
	 *            the close handler (never null)
	 */
	public void setCloseCallback(INullaryVoidFunction closeCallback) {

		this.closeCallback = closeCallback;
	}

	public void closePopupInteractive() {

		if (confirmBeforeClose) {
			popup.executeConfirm(this::executeCloseActions, DomI18n.ARE_YOU_SURE_TO_CLOSE_THIS_WINDOW_QUESTION);
		} else {
			executeCloseActions();
		}
	}

	public void closePopupNonInteractive() {

		executeCloseActions();
	}

	private void executeCloseActions() {

		if (removeChildrenOnClose) {
			popup.removeChildren();
		}
		if (closeCallback != null) {
			closeCallback.apply();
		}
		hideCallback.apply();
	}
}
