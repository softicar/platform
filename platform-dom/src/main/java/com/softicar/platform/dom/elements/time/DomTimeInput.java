package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.Optional;

public class DomTimeInput extends AbstractDomValueInputDiv<Time> {

	private final DomTextInput timeInput;

	public DomTimeInput() {

		this.timeInput = createInput();
		appendChildren(timeInput);
	}

	public void setPlaceholder(IDisplayString placeholder) {

		timeInput.setPlaceholder(placeholder);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		this.timeInput.setDisabled(disabled);
	}

	@Override
	public Optional<Time> getValue() {

		return new TimeParser(timeInput.getValueTextTrimmed()).parseOrThrow();
	}

	@Override
	public void setValue(Time time) {

		if (time != null) {
			timeInput.setValue(time.toString());
		} else {
			timeInput.setValue(null);
		}
	}

	private DomTextInput createInput() {

		var input = new DomTextInput();
		input.setTitle(DomI18n.TIME);
		input.addMarker(DomTestMarker.TIME_INPUT);
		input.addChangeCallback(DomTimeInput.this::executeChangeCallbacks);
		return input;
	}
}
