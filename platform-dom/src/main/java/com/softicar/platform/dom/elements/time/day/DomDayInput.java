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
	private INullaryVoidFunction callback;

	public DomDayInput() {

		this.dayInput = appendChild(new DayInput());
		this.dayButton = appendChild(new DayButton());
		this.callback = null;

		addCssClass(DomElementsCssClasses.DOM_DAY_INPUT);
	}

	public DomDayInput setCallback(INullaryVoidFunction callback) {

		this.callback = callback;

		if (callback != null) {
			dayInput.listenToEvent(DomEventType.CHANGE);
		} else {
			dayInput.unlistenToEvent(DomEventType.CHANGE);
		}

		return this;
	}

	public void applyCallback() {

		if (callback != null) {
			callback.apply();
		}
	}

	@Override
	public void disable() {

		dayInput.setDisabled(true);
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

		dayInput.setInputText(day != null? day.toISOString() : "");
		dayButton.setDay(day);
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

			dayInput.setInputText(getDay().toISOString());
			applyCallback();
		}
	}

	private class DayInput extends DomTextInput implements IDomEventHandler {

		public DayInput() {

			setMaxLength(10);
			setMarker(DomTestMarker.DAY_INPUT);
		}

		@Override
		public void handleDOMEvent(IDomEvent event) {

			applyCallback();
		}
	}
}
