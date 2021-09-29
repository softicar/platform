package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;

class DomDayPopup extends DomPopup {

	private final DomDayChooserDiv chooser;
	private final AbstractDomDayPopupButton button;
	private Day day;

	public DomDayPopup(DomDayChooserDiv chooser, AbstractDomDayPopupButton button) {

		this.chooser = chooser;
		this.button = button;

		setCaption(DomI18n.DAY_SELECTION);
		appendChild(chooser);
		appendActionNode(new OkayButton());
		appendActionNode(new CancelAndRestoreButton());
		setCallbackBeforeShow(this::beforeShow);
	}

	private void beforeShow() {

		day = chooser.getDay();
		setSubCaption(IDisplayString.create(chooser.getDay().toHumanString()));
	}

	private class OkayButton extends DomButton {

		public OkayButton() {

			setIcon(DomElementsImages.DIALOG_OKAY.getResource());
			setLabel(DomI18n.OK);
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			button.setDay(chooser.getDay());
			button.handleDayChange();
			hide();
		}
	}

	private class CancelAndRestoreButton extends DomButton {

		public CancelAndRestoreButton() {

			setIcon(DomElementsImages.DIALOG_CANCEL.getResource());
			setLabel(DomI18n.CANCEL);
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			chooser.setDay(day);
			hide();
		}
	}
}
