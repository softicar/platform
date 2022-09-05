package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.button.DomButton;

/**
 * A click-able button that shows the date of a {@link DomDayChooserDiv} object.
 * <p>
 * An object of this class already contains a {@link DomDayPopover} object. If
 * you change the date, the popover closes and the changes will be shown.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
abstract class AbstractDomDayPopupButton extends DomButton {

	private final DomDayPopover popup;
	private final DomDayChooserDiv chooser;
	private boolean showLabel = true;

	private AbstractDomDayPopupButton(DomDayChooserDiv chooser) {

		this.chooser = chooser;
		this.popup = new DomDayPopover(chooser, this);

		setIcon(DomImages.CALENDAR_DAY.getResource());
		setLabel(IDisplayString.create(chooser.getDay().toString()));
		setClickCallback(() -> popup.open());
	}

	public AbstractDomDayPopupButton() {

		this(new DomDayChooserDiv(Day.today()));
	}

	private void updateLabel() {

		if (showLabel) {
			setLabel(IDisplayString.create(chooser.getDay().toISOString()));
		} else {
			removeLabel();
		}
	}

	/**
	 * @return the point of time of the DOMDayAndTimeChooseForm object
	 */
	public Day getDay() {

		return chooser.getDay();
	}

	public void setDay(Day day) {

		chooser.setDay(day);
		updateLabel();
	}

	public void setShowLabel(boolean showLabel) {

		if (showLabel != this.showLabel) {
			this.showLabel = showLabel;
			updateLabel();
		}
	}

	protected void openPopup() {

		popup.open();
	}

	public abstract void handleDayChange();
}
