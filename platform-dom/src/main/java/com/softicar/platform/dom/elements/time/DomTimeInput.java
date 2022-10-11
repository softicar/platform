package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.common.date.IllegalTimeSpecificationException;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.common.string.Tokenizer;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DomTimeInput extends AbstractDomValueInputDiv<Time> {

	private final DomTextInput timeInput;

	public DomTimeInput() {

		this.timeInput = createInput(DomI18n.TIME, DomTestMarker.TIME_INPUT);
		addCssClass(DomCssClasses.DOM_TIME_INPUT);
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

		var tokens = tokenizeInputValue();
		try {
			if (tokens.isEmpty() || tokens.stream().allMatch(String::isEmpty)) {
				return Optional.empty();
			} else if (validateTokenLength(tokens)) {
				while (tokens.size() < 3) {
					tokens.add("0");
				}
				var hours = Integer.valueOf(tokens.get(0));
				var minutes = Integer.valueOf(tokens.get(1));
				var seconds = Integer.valueOf(tokens.get(2));
				return Optional.of(new Time(hours, minutes, seconds));
			} else {
				throw new IllegalTimeSpecificationException(timeInput.getValueOrNull());
			}
		} catch (Exception exception) {
			throw new IllegalTimeSpecificationException(exception, timeInput.getValueOrNull());
		}
	}

	@Override
	public void setValue(Time time) {

		if (time != null) {
			timeInput.setValue(time.toString());
		} else {
			timeInput.setValue(null);
		}
	}

	private DomTextInput createInput(IDisplayString title, ITestMarker testMarker) {

		var input = new DomTextInput();
		input.setTitle(title);
		input.addMarker(testMarker);
		input.addCssClass(DomCssClasses.DOM_TIME_INPUT_ELEMENT);
		input.addChangeCallback(DomTimeInput.this::executeChangeCallbacks);
		return input;
	}

	private List<String> tokenizeInputValue() {

		List<String> tokens = new ArrayList<>();
		var inputText = timeInput.getValueOrNull();
		if (inputText != null && !inputText.isEmpty()) {
			tokens = new Tokenizer(':', '\\').tokenize(inputText);
			if (tokens.size() > 3) {
				throw new IllegalTimeSpecificationException(inputText);
			}
		}
		return tokens;
	}

	private boolean validateTokenLength(List<String> tokens) {

		return tokens//
			.stream()
			.allMatch(it -> it.length() <= 2);
	}
}
