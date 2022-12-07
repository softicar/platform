package com.softicar.platform.dom.elements.input.auto;

import java.util.Objects;

class DomAutoCompleteValueAndStateCache<T> {

	private final DomAutoCompleteInput<T> input;
	private DomAutoCompleteValueAndState<T> valueAndState;
	private String previousValueText;

	public DomAutoCompleteValueAndStateCache(DomAutoCompleteInput<T> input) {

		this.input = Objects.requireNonNull(input);
		clear();
	}

	public void clear() {

		this.valueAndState = null;
		this.previousValueText = null;
	}

	public DomAutoCompleteValueAndState<T> getValueAndState() {

		String currentValueText = input.getValueText();
		if (!currentValueText.equals(previousValueText)) {
			this.valueAndState = new DomAutoCompleteValueParser<>(input).parse();
			this.previousValueText = currentValueText;
		}

		return Objects.requireNonNull(valueAndState);
	}
}
