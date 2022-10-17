package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayParser;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.common.string.Tokenizer;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.time.TimeParser;
import com.softicar.platform.dom.elements.time.day.AbstractDomDayPopupButton;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DomDayTimeInput extends AbstractDomValueInputDiv<DayTime> {

	private final DomTextInput dayTimeInput;
	private final PickDayButton dayButton;

	public DomDayTimeInput() {

		this.dayTimeInput = createInput();
		this.dayButton = new PickDayButton();
		appendChildren(dayTimeInput, dayButton, new FormatTooltip());
		addCssClass(DomCssClasses.DOM_DAY_TIME_INPUT);
	}

	public void setPlaceholder(IDisplayString placeholder) {

		dayTimeInput.setPlaceholder(placeholder);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		this.dayTimeInput.setDisabled(disabled);
		this.dayButton.setDisabled(disabled);
	}

	@Override
	public Optional<DayTime> getValue() {

		List<String> dayTimeTokens = getDayTimeTokens();
		if (dayTimeTokens.isEmpty()) {
			return Optional.empty();
		} else {
			Optional<Day> day = Optional.of(new DayParser(dayTimeTokens.get(0)).parseOrThrow());
			Optional<Time> time = new TimeParser(dayTimeTokens.get(1)).parseOrThrow();
			if (day.isPresent() && time.isPresent()) {
				return Optional.of(new DayTime(day.get(), time.get()));
			} else if (!day.isPresent() && !time.isPresent()) {
				return Optional.empty();
			} else {
				if (time.isPresent()) {
					throw new SofticarUserException(CommonDateI18n.MISSING_DATE_SPECIFICATION);
				} else {
					throw new SofticarUserException(CommonDateI18n.MISSING_TIME_SPECIFICATION);
				}
			}
		}
	}

	@Override
	public void setValue(DayTime dayTime) {

		this.dayTimeInput.setValue(dayTime != null? dayTime.toLocalizedString() : null);
		this.dayButton.setDay(dayTime != null? dayTime.getDay() : null);
	}

	private DomTextInput createInput() {

		var input = new DomTextInput();
		input.addChangeCallback(DomDayTimeInput.this::executeChangeCallbacks);
		input.addMarker(DomTestMarker.DAY_TIME_INPUT);
		return input;
	}

	private List<String> getDayTimeTokens() {

		var dayTimeString = dayTimeInput.getValueTextTrimmed();
		if (dayTimeString.isBlank()) {
			return Collections.emptyList();
		} else {
			var tokens = new Tokenizer(' ', '\\').tokenize(dayTimeString);
			if (tokens.size() > 2) {
				throw new SofticarUserException(CommonDateI18n.ILLEGAL_DAY_TIME_SPECIFICATION_ARG1.toDisplay(dayTimeString));
			}
			if (tokens.size() < 2) {
				tokens.add("");
			}
			return tokens;
		}
	}

	private class PickDayButton extends AbstractDomDayPopupButton {

		public PickDayButton() {

			setTitle(DomI18n.PICK_A_DAY);
			setShowLabel(false);
			setTabIndex(-1);
			setClickCallback(() -> {
				getValueNoThrow().ifPresent(dayTime -> setDay(dayTime.getDay()));
				openPopup();
			});
		}

		@Override
		public void handleDayChange() {

			dayTimeInput.setValue(getDay().toLocalizedString() + " 00:00:00");
			executeChangeCallbacks();
		}
	}

	private class FormatTooltip extends DomDiv {

		public FormatTooltip() {

			addCssClass(DomCssClasses.DOM_DAY_TIME_INPUT_TOOLTIP);
			var dateFormat = Optional//
				.ofNullable(CurrentBasicUser.get())
				.map(IBasicUser::getLocale)
				.map(ILocale::getDateFormat)
				.orElse("dd.mm.yyyy");
			appendText(dateFormat + " hh:mm:ss");
		}
	}
}
