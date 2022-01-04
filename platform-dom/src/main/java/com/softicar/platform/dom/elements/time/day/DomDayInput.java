package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.date.DateUtils;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayParser;
import com.softicar.platform.common.date.IllegalDateSpecificationException;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.Optional;

/**
 * A text input field combined with a day button.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class DomDayInput extends DomBar {

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

	/**
	 * Parses the value text into a {@link Day}.
	 * <p>
	 * If the value text is empty or blank, an empty {@link Optional} is
	 * returned. Otherwise, the value text is parsed into a {@link Day}. If
	 * parsing failed, an exception is thrown.
	 *
	 * @return the optionally entered {@link Day}
	 * @throws IllegalDateSpecificationException
	 *             if the non-blank value text could not be parsed
	 */
	public Optional<Day> retrieveValue() {

		String value = dayInput.getValue();
		if (value != null && !value.isBlank()) {
			return Optional.of(new DayParser(value).parseOrThrow());
		} else {
			return Optional.empty();
		}
	}

	/**
	 * @deprecated use {@link #retrieveValue()}
	 */
	@Deprecated
	public Day getDayOrNull() {

		return DateUtils.parseDate(dayInput.getValue());
	}

	/**
	 * @deprecated use {@link #retrieveValue()}
	 */
	@Deprecated
	public Day getDay() {

		Day day = getDayOrNull();
		if (day == null) {
			IDisplayString message = DomI18n.THE_TEXT_ARG1_DOES_NOT_REPRESENT_A_VALID_DATE
				.toDisplay(dayInput.getValue())
				.concat(" ")
				.concat(DomI18n.VALID_FORMATS)
				.concat(" 2000-12-31, 31.12.2000, 12/31/2000");
			throw new SofticarUserException(message);
		}
		return day;
	}

	public void setDay(Day day) {

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

	public void clear() {

		setDay(null);
	}

	public DomTextInput getTextBoxInput() {

		return dayInput;
	}

	private class DayButton extends AbstractDomDayPopupButton {

		public DayButton() {

			setShowLabel(false);
			setTabIndex(-1);
			setClickCallback(() -> {
				Day day = getDayOrNull();
				if (day != null) {
					setDay(day);
				}
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

		@Override
		public void setValue(String value) {

			setValue(value, true);
		}

		public void setValue(String value, boolean triggerCallback) {

			super.setValue(value);
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
