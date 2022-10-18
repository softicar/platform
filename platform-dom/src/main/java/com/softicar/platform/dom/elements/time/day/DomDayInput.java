package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayParser;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.input.DomValueInputTooltip;
import java.util.Optional;

/**
 * A textual input element to enter a {@link Day} value.
 * <p>
 * Contains a {@link DomButton} to spawn a day-picker popover.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 * @author Oliver Richers
 */
public class DomDayInput extends AbstractDomValueInputDiv<Day> {

	private final DomTextInput dayInput;
	private final PickDayButton dayButton;

	public DomDayInput() {

		this.dayInput = createDayInput();
		this.dayButton = new PickDayButton();

		var tooltipText = CurrentLocale.get().getLocalizedDateFormat();
		var tooltip = new DomValueInputTooltip(tooltipText);

		appendChild(dayInput);
		appendChild(dayButton);
		appendChild(tooltip);

		addCssClass(DomCssClasses.DOM_DAY_INPUT);
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

		dayInput.setValue(day != null? day.toLocalizedString() : "");
		dayButton.setDay(day);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		this.dayInput.setDisabled(disabled);
		this.dayButton.setDisabled(disabled);
	}

	public void setPlaceholder(IDisplayString placeholder) {

		dayInput.setPlaceholder(placeholder);
	}

	private void handleChange() {

		getValueNoThrow().ifPresent(this::setValue);
	}

	private DomTextInput createDayInput() {

		var input = new DomTextInput();
		input.setMaxLength(10);
		input.addChangeCallback(this::handleChange);
		input.addChangeCallback(DomDayInput.this::executeChangeCallbacks);
		input.addMarker(DomTestMarker.DAY_INPUT);
		return input;
	}

	private class PickDayButton extends AbstractDomDayPopupButton {

		public PickDayButton() {

			setTitle(DomI18n.PICK_A_DAY);
			setShowLabel(false);
			setTabIndex(-1);
			setClickCallback(() -> {
				getValueNoThrow().ifPresent(this::setDay);
				openPopup();
			});
		}

		@Override
		public void handleDayChange() {

			dayInput.setValue(getDay().toLocalizedString());
			executeChangeCallbacks();
		}
	}
}
