package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.utils.CastUtils;

class DomAutoCompleteValueParser<T> {

	private final DomAutoCompleteInput<T> input;

	public DomAutoCompleteValueParser(DomAutoCompleteInput<T> input) {

		this.input = input;
	}

	public DomAutoCompleteValueAndState<T> parse() {

		if (input.isBlank()) {
			return createResult(DomAutoCompleteValueState.NONE);
		} else if (input.getValidationMode().isPermissive()) {
			// TODO PLAT-753 This cast should not be necessary. Permissive mode should not even be handled in the same auto-complete input implementation.
			return createResult(CastUtils.<T> cast(input.getValueText()));
		} else {
			var matchingValues = input.getInputEngine().findMatches(input.getPattern(), 2);
			if (matchingValues.isEmpty()) {
				return createResult(DomAutoCompleteValueState.ILLEGAL);
			} else {
				var firstMatchingValue = matchingValues.iterator().next();
				if (matchingValues.size() == 1 || isPerfectMatch(firstMatchingValue)) {
					return createResult(firstMatchingValue);
				} else {
					return createResult(DomAutoCompleteValueState.AMBIGUOUS);
				}
			}
		}
	}

	private DomAutoCompleteValueAndState<T> createResult(DomAutoCompleteValueState state) {

		return new DomAutoCompleteValueAndState<>(null, state);
	}

	private DomAutoCompleteValueAndState<T> createResult(T value) {

		return new DomAutoCompleteValueAndState<>(value, DomAutoCompleteValueState.VALID);
	}

	private boolean isPerfectMatch(T value) {

		return input//
			.getInputEngine()
			.getDisplayString(value)
			.toString()
			.equalsIgnoreCase(input.getPattern());
	}
}
