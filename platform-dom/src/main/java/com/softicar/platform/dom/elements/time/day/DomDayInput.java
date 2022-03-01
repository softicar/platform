package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayParser;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.input.IDomValueInput;
import java.util.Optional;

/**
 * A text input field combined with a day button.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class DomDayInput extends DomBar implements IDomValueInput<Day> {

	private final DayInput dayInput;
	private final DayButton dayButton;

	public DomDayInput() {

		this(Day.today());
	}

	public DomDayInput(Day day) {

		this.dayInput = appendChild(new DayInput());
		this.dayButton = appendChild(new DayButton());
		setDay(day, false);
		addCssClass(DomElementsCssClasses.DOM_DAY_INPUT);
	}

	public DomDayInput setCallback(INullaryVoidFunction callback) {

		dayInput.setCallback(callback);
		return this;
	}

	public void disable() {

		dayInput.disable();
		dayButton.unlistenToEvent(DomEventType.CLICK);
	}

	@Override
	public Optional<Day> getValue() {

		String inputText = dayInput.getInputTextTrimmed();
		if (!inputText.isBlank()) {
			return Optional.of(new DayParser(inputText).parseOrThrow());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void setValue(Day day) {

		setDay(day, true);
	}

	private void setDay(Day day, boolean triggerCallback) {

		if (day != null) {
			dayInput.setValue(day.toISOString(), triggerCallback);
			dayButton.setDay(day);
		} else {
			dayInput.setValue("", triggerCallback);
			dayButton.setDay(Day.today());
		}
	}

	public DomTextInput getTextBoxInput() {

		return dayInput;
	}

	private class DayButton extends AbstractDomDayPopupButton {

		public DayButton() {

			setShowLabel(false);
			setTabIndex(-1);
			setClickCallback(() -> {
				getValueNoThrow().ifPresent(this::setDay);
				showPopup();
			});
		}

		@Override
		public void handleDayChange() {

			dayInput.setValue(getDay().toISOString());
		}
	}

	private class DayInput extends DomTextInput implements IDomEventHandler {

		private INullaryVoidFunction callback;

		public DayInput() {

			setMaxLength(10);
			setMarker(DomTestMarker.DAY_INPUT);
		}

		public void setCallback(INullaryVoidFunction callback) {

			this.callback = callback;
			if (callback != null) {
				listenToEvent(DomEventType.CHANGE);
			} else {
				unlistenToEvent(DomEventType.CHANGE);
			}
		}

		@Override
		public void handleDOMEvent(IDomEvent event) {

			applyCallback();
		}

		public void setValue(String value) {

			setValue(value, true);
		}

		public void setValue(String value, boolean triggerCallback) {

			setInputText(value);
			if (triggerCallback) {
				applyCallback();
			}
		}

		private void applyCallback() {

			if (callback != null) {
				callback.apply();
			}
		}
	}
}
