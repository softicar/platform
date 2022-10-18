package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.input.DomValueInputTooltip;
import java.util.Optional;

/**
 * A textual input element to enter a {@link Time} value.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 * @author Oliver Richers
 */
public class DomTimeInput extends AbstractDomValueInputDiv<Time> {

	private final DomTextInput timeInput;

	public DomTimeInput() {

		this.timeInput = createInput();

		var tooltipText = CurrentLocale.get().getLocalizedTimeFormat();
		var tooltip = new DomValueInputTooltip(tooltipText);

		appendChild(timeInput);
		appendChild(tooltip);

		addCssClass(DomCssClasses.DOM_TIME_INPUT);
	}

	@Override
	public Optional<Time> getValue() {

		return new DomTimeInputParser(timeInput.getValueTextTrimmed()).parseOrThrow();
	}

	@Override
	public void setValue(Time time) {

		if (time != null) {
			timeInput.setValue(time.toIsoFormat());
		} else {
			timeInput.setValue(null);
		}
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		this.timeInput.setDisabled(disabled);
	}

	public void setPlaceholder(IDisplayString placeholder) {

		timeInput.setPlaceholder(placeholder);
	}

	private void handleChange() {

		getValueNoThrow().ifPresent(this::setValue);
	}

	private DomTextInput createInput() {

		var input = new DomTextInput();
		input.addMarker(DomTestMarker.TIME_INPUT);
		input.addChangeCallback(this::handleChange);
		input.addChangeCallback(DomTimeInput.this::executeChangeCallbacks);
		return input;
	}
}
