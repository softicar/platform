package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.common.date.IllegalTimeSpecificationException;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.label.DomPreformattedLabel;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import java.util.Optional;

public class DomTimeInput extends AbstractDomValueInputDiv<Time> {

	private final DomIntegerInput hourInput;
	private final DomIntegerInput minuteInput;
	private final DomIntegerInput secondInput;

	public DomTimeInput() {

		this.hourInput = createInput(DomI18n.HOURS, DomTestMarker.TIME_INPUT_HOURS_INPUT);
		this.minuteInput = createInput(DomI18n.MINUTES, DomTestMarker.TIME_INPUT_MINUTES_INPUT);
		this.secondInput = createInput(DomI18n.SECONDS, DomTestMarker.TIME_INPUT_SECONDS_INPUT);

		addCssClass(DomCssClasses.DOM_TIME_INPUT);
		appendChildren(hourInput, new DomPreformattedLabel(":"), minuteInput, new DomPreformattedLabel(":"), secondInput);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		this.hourInput.setDisabled(disabled);
		this.minuteInput.setDisabled(disabled);
		this.secondInput.setDisabled(disabled);
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
			hourInput.setValue(null);
			minuteInput.setValue(null);
			secondInput.setValue(null);
		}
	}

	private String getValueAsString() {

		return "%s:%s:%s"
			.formatted(//
				hourInput.getTextualValue(),
				minuteInput.getTextualValue(),
				secondInput.getTextualValue());
	}

	private DomIntegerInput createInput(IDisplayString title, ITestMarker testMarker) {

		var input = new DomIntegerInput();
		input.setTitle(title);
		input.addMarker(testMarker);
		input.addCssClass(DomCssClasses.DOM_TIME_INPUT_ELEMENT);
		input.addChangeCallback(DomTimeInput.this::executeChangeCallbacks);
		return input;
	}
}
