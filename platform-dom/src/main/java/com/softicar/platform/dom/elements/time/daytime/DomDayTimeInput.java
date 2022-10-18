package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.time.day.AbstractDomDayPopupButton;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.input.DomValueInputTooltip;
import java.util.Optional;

/**
 * A textual input element to enter a {@link DayTime} value.
 * <p>
 * Contains a {@link DomButton} to spawn a day-picker popover.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 * @author Oliver Richers
 */
public class DomDayTimeInput extends AbstractDomValueInputDiv<DayTime> {

	private final DomTextInput dayTimeInput;
	private final PickDayButton dayButton;

	public DomDayTimeInput() {

		this.dayTimeInput = createInput();
		this.dayButton = new PickDayButton();

		var tooltipText = CurrentLocale.get().getLocalizedDayTimeFormat();
		var tooltip = new DomValueInputTooltip(tooltipText);

		appendChild(dayTimeInput);
		appendChild(dayButton);
		appendChild(tooltip);

		addCssClass(DomCssClasses.DOM_DAY_TIME_INPUT);
	}

	@Override
	public Optional<DayTime> getValue() {

		return new DomDayTimeInputParser(dayTimeInput.getValueTextTrimmed()).parseOrThrow();
	}

	@Override
	public void setValue(DayTime dayTime) {

		this.dayTimeInput.setValue(dayTime != null? dayTime.toLocalizedString() : null);
		this.dayButton.setDay(dayTime != null? dayTime.getDay() : null);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		this.dayTimeInput.setDisabled(disabled);
		this.dayButton.setDisabled(disabled);
	}

	public void setPlaceholder(IDisplayString placeholder) {

		dayTimeInput.setPlaceholder(placeholder);
	}

	private void handleChange() {

		getValueNoThrow().ifPresent(this::setValue);
	}

	private DomTextInput createInput() {

		var input = new DomTextInput();
		input.addChangeCallback(this::handleChange);
		input.addChangeCallback(DomDayTimeInput.this::executeChangeCallbacks);
		input.addMarker(DomTestMarker.DAY_TIME_INPUT);
		return input;
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

			var previousTime = getValueNoThrow().map(DayTime::getTime).orElse(new Time(0, 0, 0));
			dayTimeInput.setValue(getDay().toLocalizedString() + " " + previousTime.toIsoFormat());
			executeChangeCallbacks();
		}
	}
}
