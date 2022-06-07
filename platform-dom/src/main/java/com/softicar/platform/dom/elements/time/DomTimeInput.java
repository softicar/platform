package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.date.IllegalTimeSpecificationException;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.label.DomPreformattedLabel;
import com.softicar.platform.dom.input.AbstractDomValueInput;
import java.util.Optional;

public class DomTimeInput extends AbstractDomValueInput<Time> {

	private final DomIntegerInput hourInput;
	private final DomIntegerInput minuteInput;
	private final DomIntegerInput secondInput;

	public DomTimeInput() {

		this.hourInput = new Input(DomI18n.HOURS, DomTestMarker.HOURS_INPUT);
		this.minuteInput = new Input(DomI18n.MINUTES, DomTestMarker.MINUTES_INPUT);
		this.secondInput = new Input(DomI18n.SECONDS, DomTestMarker.SECONDS_INPUT);

		addCssClass(DomElementsCssClasses.DOM_TIME_INPUT);
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
				hourInput.getInputText(),
				minuteInput.getInputText(),
				secondInput.getInputText());
	}

	private class Input extends DomIntegerInput {

		public Input(IDisplayString title, IStaticObject testMarker) {

			setTitle(title);
			addMarker(testMarker);
			addCssClass(DomElementsCssClasses.DOM_TIME_INPUT_ELEMENT);
			addChangeCallback(DomTimeInput.this::executeChangeCallbacks);
		}
	}
}
