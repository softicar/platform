package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.IllegalTimeSpecificationException;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.label.DomPreformattedLabel;
import com.softicar.platform.dom.elements.tables.DomDataTable;
import java.util.Optional;

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

		hourInput = new DomIntegerInput().setRange(0, 23);
		hourInput.setTitle(DomI18n.HOURS);
		hourInput.setMarker(DomTestMarker.HOUR_INPUT);
		hourInput.addCssClass(DomElementsCssClasses.DOM_TIME_INPUT_ELEMENT);

		minuteInput = new DomIntegerInput().setRange(0, 59);
		minuteInput.setTitle(DomI18n.MINUTES);
		minuteInput.setMarker(DomTestMarker.MINUTES_INPUT);
		minuteInput.addCssClass(DomElementsCssClasses.DOM_TIME_INPUT_ELEMENT);

		secondInput = new DomIntegerInput().setRange(0, 59);
		secondInput.setTitle(DomI18n.SECONDS);
		secondInput.setMarker(DomTestMarker.SECONDS_INPUT);
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

	/**
	 * Parses the value text into a {@link Time} object.
	 * <p>
	 * If the value text is empty or blank, an empty {@link Optional} is
	 * returned. Otherwise, the value text is parsed into a {@link Time} object.
	 * If parsing failed, an exception is thrown.
	 *
	 * @return the optionally entered {@link Time}
	 * @throws IllegalTimeSpecificationException
	 *             if the non-blank value text could not be parsed
	 */
	public Optional<Time> getTimeOrThrowIfInvalid() {

		return Optional.ofNullable(getTime());
	}

	public Time getTime() {

		try {
			Integer hours = hourInput.getIntegerOrNull();
			Integer minutes = minuteInput.getIntegerOrNull();
			Integer seconds = secondInput.getIntegerOrNull();

			if (hours != null && minutes != null && seconds != null) {
				return new Time(hours, minutes, seconds);
			} else if (hours == null && minutes == null && seconds == null) {
				return null;
			} else {
				throw new IllegalTimeSpecificationException(getValueAsString());
			}
		} catch (Exception exception) {
			throw new IllegalTimeSpecificationException(exception, getValueAsString());
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

	private String getValueAsString() {

		return "%s:%s:%s"
			.formatted(//
				Optional.ofNullable(hourInput.getValue()).orElse(""),
				Optional.ofNullable(minuteInput.getValue()).orElse(""),
				Optional.ofNullable(secondInput.getValue()).orElse(""));
	}
}
