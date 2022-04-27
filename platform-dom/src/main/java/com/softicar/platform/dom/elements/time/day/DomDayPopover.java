package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;

class DomDayPopover extends DomPopover {

	private final AbstractDomDayPopupButton button;

	public DomDayPopover(DomDayChooserDiv chooser, AbstractDomDayPopupButton button) {

		this.button = button;
		appendChild(chooser.setDayConsumer(this::chooseDay));
	}

	private void chooseDay(Day day) {

		button.setDay(day);
		button.handleDayChange();
		closeWithoutConfirm();
	}
}
