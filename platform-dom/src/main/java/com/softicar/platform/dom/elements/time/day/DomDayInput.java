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
import java.util.Objects;
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
	private boolean disabled;

	public DomDayInput() {

		this.dayInput = appendChild(new DayInput());
		this.dayButton = appendChild(new DayButton());
		this.callback = INullaryVoidFunction.NO_OPERATION;
		this.disabled = false;

		addCssClass(DomElementsCssClasses.DOM_DAY_INPUT);
	}

	@Override
	public DomDayInput setDisabled(boolean disabled) {

		this.dayInput.setDisabled(disabled);
		this.dayButton.setDisabled(disabled);
		this.disabled = disabled;
		return this;
	}

	@Override
	public boolean isDisabled() {

		return disabled;
	}

	@Override
	public final DomDayInput setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	@Override
	public final boolean isEnabled() {

		return !isDisabled();
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

	protected DomDayInput setCallback(INullaryVoidFunction callback) {

		this.callback = Objects.requireNonNull(callback);
		this.dayInput.listenToEvent(DomEventType.CHANGE);
		return this;
	}

	protected void applyCallback() {

		callback.apply();
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
			applyCallback();
		}
	}

	private class DayInput extends DomTextInput implements IDomEventHandler {

		public DayInput() {

			setMaxLength(10);
			addMarker(DomTestMarker.DAY_INPUT);
		}

		@Override
		public void handleDOMEvent(IDomEvent event) {

			applyCallback();
		}
	}
}
