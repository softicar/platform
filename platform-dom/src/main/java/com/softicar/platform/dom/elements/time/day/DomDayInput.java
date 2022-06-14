package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayParser;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.Optional;

/**
 * A text input field combined with a day button.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class DomDayInput extends AbstractDomValueInputDiv<Day> {

	private final DomTextInput dayInput;
	private final DayButton dayButton;

	public DomDayInput() {

		this.dayInput = createDayInput();
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

		var text = dayInput.getValueTextTrimmed();
		if (!text.isBlank()) {
			return Optional.of(new DayParser(text).parseOrThrow());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void setValue(Day day) {

		dayInput.setValue(day != null? day.toISOString() : "");
		dayButton.setDay(day);
	}

	private DomTextInput createDayInput() {

		var input = new DomTextInput();
		input.setMaxLength(10);
		input.addChangeCallback(DomDayInput.this::executeChangeCallbacks);
		input.addMarker(DomTestMarker.DAY_INPUT);
		return input;
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

			dayInput.setValue(getDay().toISOString());
			executeChangeCallbacks();
		}
	}
}
