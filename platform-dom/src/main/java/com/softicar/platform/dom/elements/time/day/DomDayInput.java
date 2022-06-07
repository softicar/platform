package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayParser;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.AbstractDomValueInput;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.Optional;

/**
 * A text input field combined with a day button.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class DomDayInput extends AbstractDomValueInput<Day> {

	private final DayInput dayInput;
	private final DayButton dayButton;

	public DomDayInput() {

		this.dayInput = new DayInput();
		this.dayButton = new DayButton();

		addCssClass(DomElementsCssClasses.DOM_DAY_INPUT);
		appendChildren(dayInput, dayButton);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		this.dayInput.setDisabled(disabled);
		this.dayButton.setDisabled(disabled);
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

	private class DayButton extends AbstractDomDayPopupButton {

		public DayButton() {

			setShowLabel(false);
			setTabIndex(-1);
			setClickCallback(() -> {
				getValueNoThrow().ifPresent(this::setDay);
				openPopup();
			});
		}

		@Override
		public void handleDayChange() {

			dayInput.setInputText(getDay().toISOString());
			executeChangeCallbacks();
		}
	}

	private class DayInput extends DomTextInput implements IDomChangeEventHandler {

		public DayInput() {

			setMaxLength(10);
			addMarker(DomTestMarker.DAY_INPUT);
		}

		@Override
		public void handleChange(IDomEvent event) {

			executeChangeCallbacks();
		}
	}
}
