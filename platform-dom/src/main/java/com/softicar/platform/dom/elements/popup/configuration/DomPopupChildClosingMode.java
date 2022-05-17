package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.node.IDomNode;

/**
 * Enumerates possible child-popup-closing modes of a {@link DomPopup}.
 * <p>
 * Definition: There is a parent-child relation between two {@link DomPopup}
 * elements P1 and P2 if, and only if, P2 was opened from an {@link IDomNode} in
 * P1.
 *
 * @author Alexander Schmidt
 */
public enum DomPopupChildClosingMode {

	/**
	 * If the parent {@link DomPopup} is closed, all of its children are
	 * automatically closed as well.
	 */
	AUTOMATIC_ALL(false, true),

	/**
	 * If the parent {@link DomPopup} is closed, its children are <b>not</b>
	 * automatically closed.
	 */
	AUTOMATIC_NONE(false, false),

	/**
	 * If the parent {@link DomPopup} is closed, the user is prompted. They may
	 * choose either option:
	 * <ol>
	 * <li>Close the parent {@link DomPopup} and all of its children.</li>
	 * <li>Do not close anything.</li>
	 * </ol>
	 */
	INTERACTIVE_ALL(true, true),

	/**
	 * If the {@link DomPopup} is closed, the user is prompted. They may choose
	 * either option:
	 * <ol>
	 * <li>Close only the parent {@link DomPopup}.</li>
	 * <li>Close the parent {@link DomPopup} and all of its children.</li>
	 * <li>Do not close anything.</li>
	 * </ol>
	 */
	INTERACTIVE_OPTIONAL(true, false);

	private final boolean interactive;
	private final boolean closeAll;

	private DomPopupChildClosingMode(boolean interactive, boolean closeAll) {

		this.interactive = interactive;
		this.closeAll = closeAll;
	}

	public boolean isInteractive() {

		return interactive;
	}

	public boolean isCloseAll() {

		return closeAll;
	}
}
