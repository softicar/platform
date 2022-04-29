package com.softicar.platform.dom.elements.button.popup.help;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.modal.DomDismissablePopup;
import com.softicar.platform.dom.node.IDomNode;

/**
 * Clicked to display a popup containing a help text.
 *
 * @author Alexander Schmidt
 */
public class DomHelpPopupButton extends DomButton {

	private final IDisplayString popupCaption;
	private final IDisplayString popupSubCaption;
	private IDisplayString popupHelpText = null;
	private IDomNode popupHelpTextNode = null;
	private DomPopup popup = null;

	/**
	 * @param popupCaption
	 *            The caption of the popup to be displayed.
	 * @param popupSubCaption
	 *            The sub-caption of the popup to be displayed.
	 * @param popupHelpText
	 *            The help text to be displayed in the popup.
	 */
	public DomHelpPopupButton(IDisplayString popupCaption, IDisplayString popupSubCaption, IDisplayString popupHelpText) {

		this(popupCaption, popupSubCaption);

		this.popupHelpText = popupHelpText;
	}

	/**
	 * @param popupCaption
	 * @param popupSubCaption
	 * @param popupHelpTextNode
	 */
	public DomHelpPopupButton(IDisplayString popupCaption, IDisplayString popupSubCaption, IDomNode popupHelpTextNode) {

		this(popupCaption, popupSubCaption);

		this.popupHelpTextNode = popupHelpTextNode;
	}

	private DomHelpPopupButton(IDisplayString popupCaption, IDisplayString popupSubCaption) {

		this.popupCaption = popupCaption;
		this.popupSubCaption = popupSubCaption;

		setIcon(DomElementsImages.HELP.getResource());
		setTitle(DomI18n.DISPLAY_HELP);
		setClickCallback(this::handleClick);
	}

	private void handleClick() {

		getPopup().open();
	}

	private DomPopup getPopup() {

		if (this.popup == null) {
			this.popup = new HelpPopup();
		}

		return popup;
	}

	private class HelpPopup extends DomDismissablePopup {

		public HelpPopup() {

			setCaption(popupCaption);
			setSubCaption(popupSubCaption);

			removeChildren();

			if (popupHelpText != null) {
				appendChild(new DomMessageDiv(DomMessageType.INFO, popupHelpText));
			} else {
				appendChild(popupHelpTextNode);
			}

			appendCloseButton();
		}
	}
}
