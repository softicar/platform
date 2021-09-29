package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.label.DomPreformattedLabel;
import com.softicar.platform.dom.elements.tables.DomDataTable;

public class DomTimeInput extends DomBar {

	private final DomIntegerInput hourInput;
	private final DomIntegerInput minuteInput;
	private final DomIntegerInput secondInput;

	public DomTimeInput() {

		this(DayTime.now().getTime());
	}

	public DomTimeInput(Time time) {

		this(time, false);
	}

	public DomTimeInput(Time time, boolean showLabels) {

		hourInput = new DomIntegerInput();
		hourInput.setTitle(DomI18n.HOURS);
		hourInput.addCssClass(DomElementsCssClasses.DOM_TIME_INPUT_ELEMENT);

		minuteInput = new DomIntegerInput();
		minuteInput.setTitle(DomI18n.MINUTES);
		minuteInput.addCssClass(DomElementsCssClasses.DOM_TIME_INPUT_ELEMENT);

		secondInput = new DomIntegerInput();
		secondInput.setTitle(DomI18n.SECONDS);
		secondInput.addCssClass(DomElementsCssClasses.DOM_TIME_INPUT_ELEMENT);

		if (showLabels) {
			DomDataTable table = new DomDataTable();
			table
				.getHead()
				.appendRow()
				.appendHeaderCells(//
					DomI18n.HOURS,
					DomI18n.MINUTES,
					DomI18n.SECONDS);
			table.getBody().appendRow().appendCells(hourInput, minuteInput, secondInput);
			appendChild(table);
		} else {
			appendChildren(hourInput, new DomPreformattedLabel(":"), minuteInput, new DomPreformattedLabel(":"), secondInput);
		}
		setTime(time);

		addCssClass(DomElementsCssClasses.DOM_TIME_INPUT);
	}

	public Time getTime() {

		Integer hours = hourInput.getIntegerOrNull();
		Integer minutes = minuteInput.getIntegerOrNull();
		Integer seconds = secondInput.getIntegerOrNull();
		if (hours != null && minutes != null && seconds != null) {
			return new Time(hours, minutes, seconds);
		} else if (hours == null && minutes == null && seconds == null) {
			return null;
		} else {
			throw new SofticarUserException(DomI18n.//
					CANNOT_PARSE_TIME_WITH_ARG1_HOURS_ARG2_MINUTES_AND_ARG3_SECONDS.toDisplay(hours, minutes, seconds));
		}
	}

	public void setTime(Time time) {

		if (time != null) {
			hourInput.setInteger(time.getHour());
			minuteInput.setInteger(time.getMinute());
			secondInput.setInteger(time.getSecond());
		} else {
			clear();
		}
	}

	public void clear() {

		hourInput.setInteger(null);
		minuteInput.setInteger(null);
		secondInput.setInteger(null);
	}

	public void disableSeconds() {

		secondInput.setInteger(0);
		secondInput.disable();
	}
}
