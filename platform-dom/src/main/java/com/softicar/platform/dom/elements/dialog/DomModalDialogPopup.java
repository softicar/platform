package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.style.CssPercent;

/**
 * A basic, custom modal dialog.
 * <p>
 * Enforces user interaction as follows:
 * <ul>
 * <li>Adds a <i>backdrop</i> element to the document, as an overlay above all
 * other displayed elements. The dialog is the only element that is displayed
 * above the backdrop. It is displayed as long as the modal dialog is displayed.
 * The backdrop intercepts mouse clicks.</li>
 * <li>Traps the <i>TAB</i> key focus. That is, attempts to focus elements
 * outside of the modal dialog via <i>TAB</i> or <i>Shift-TAB</i> are
 * intercepted, such that the focus cycles inside the modal dialog.</li>
 * </ul>
 *
 * @author Alexander Schmidt
 */
public class DomModalDialogPopup extends DomPopup {

	private final IDomParentElement content;

	/**
	 * Constructs a new {@link DomModalAlertPopup}
	 */
	public DomModalDialogPopup() {

		addCssClass(DomElementsCssClasses.DOM_MODAL_DIALOG_POPUP);
		setMarker(DomModalDialogMarker.POPUP);

		this.content = appendChild(new Content());

		this.configuration.setPositionStrategyByViewportCenter(CssPercent._50, CssPercent._25);
		this.configuration.setDisplayModeDialog();
		this.configuration.setFrameMarker(DomModalDialogMarker.FRAME);
	}

	/**
	 * Returns the content container element.
	 * <p>
	 * Usually, text labels or input elements are appended to the content
	 * container.
	 *
	 * @return the content container (never <i>null</i>)
	 */
	public IDomParentElement getContent() {

		return content;
	}

	private class Content extends DomDiv {

		public Content() {

			addCssClass(DomElementsCssClasses.DOM_MODAL_DIALOG_POPUP_CONTENT);
			setMarker(DomModalDialogMarker.CONTENT);
		}
	}
}
