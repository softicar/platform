package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.IDomInputNode;

/**
 * A click-able button that shows the date of a {@link DomDayChooserDiv} object.
 * <p>
 * An object of this class already contains a {@link DomDayPopup} object. If you
 * change the date and close the pop-up menuToClose the changes you have made
 * will be shown.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public abstract class AbstractDomDayPopupButton extends DomButton implements IDomInputNode {

	private final DomDayPopup popup;
	private final DomDayChooserDiv chooser;
	private boolean showLabel = true;

	private AbstractDomDayPopupButton(DomDayChooserDiv chooser) {

		this.chooser = chooser;
		this.popup = new DomDayPopup(chooser, this);

		setIcon(DomElementsImages.CALENDAR_DAY.getResource());
		setLabel(IDisplayString.create(chooser.getDay().toString()));
		setClickCallback(() -> popup.show());
	}

	public AbstractDomDayPopupButton(Day day) {

		this(new DomDayChooserDiv(day));
	}

	public AbstractDomDayPopupButton() {

		this(new DomDayChooserDiv(Day.today()));
	}

	private void updateLabel() {

		setLabel(IDisplayString.create(showLabel? chooser.getDay().toISOString() : ""));
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

	protected void showPopup() {

		popup.show();
	}

	public abstract void handleDayChange();
}
