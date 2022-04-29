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
import com.softicar.platform.dom.input.IDomValueInput;
import java.util.Optional;

public class DomTimeInput extends DomBar implements IDomValueInput<Time> {

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
		hourInput.addMarker(DomTestMarker.HOURS_INPUT);
		hourInput.addCssClass(DomElementsCssClasses.DOM_TIME_INPUT_ELEMENT);

		minuteInput = new DomIntegerInput();
		minuteInput.setTitle(DomI18n.MINUTES);
		minuteInput.addMarker(DomTestMarker.MINUTES_INPUT);
		minuteInput.addCssClass(DomElementsCssClasses.DOM_TIME_INPUT_ELEMENT);

		secondInput = new DomIntegerInput();
		secondInput.setTitle(DomI18n.SECONDS);
		secondInput.addMarker(DomTestMarker.SECONDS_INPUT);
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
		setValue(time);

		addCssClass(DomElementsCssClasses.DOM_TIME_INPUT);
	}

	@Override
	public Optional<Time> getValue() {

		try {
			Integer hours = hourInput.getValue().orElse(null);
			Integer minutes = minuteInput.getValue().orElse(null);
			Integer seconds = secondInput.getValue().orElse(null);

			if (hours != null && minutes != null && seconds != null) {
				return Optional.of(new Time(hours, minutes, seconds));
			} else if (hours == null && minutes == null && seconds == null) {
				return Optional.empty();
			} else {
				throw new IllegalTimeSpecificationException(getValueAsString());
			}
		} catch (Exception exception) {
			throw new IllegalTimeSpecificationException(exception, getValueAsString());
		}
	}

	@Override
	public void setValue(Time time) {

		if (time != null) {
			hourInput.setValue(time.getHour());
			minuteInput.setValue(time.getMinute());
			secondInput.setValue(time.getSecond());
		} else {
			clear();
		}
	}

	public void clear() {

		hourInput.setValue(null);
		minuteInput.setValue(null);
		secondInput.setValue(null);
	}

	public void disableSeconds() {

		secondInput.setValue(0);
		secondInput.setDisabled(true);
	}

	private String getValueAsString() {

		return "%s:%s:%s"
			.formatted(//
				hourInput.getInputText(),
				minuteInput.getInputText(),
				secondInput.getInputText());
	}
}
