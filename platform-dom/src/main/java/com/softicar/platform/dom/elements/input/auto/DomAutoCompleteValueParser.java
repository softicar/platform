package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.utils.CastUtils;

class DomAutoCompleteValueParser<T> {

	private final DomAutoCompleteInput<T> input;

	public DomAutoCompleteValueParser(DomAutoCompleteInput<T> input) {

		this.input = input;
	}

	public DomAutoCompleteStatefulValue<T> parse() {

		if (input.isBlank()) {
			return createStatefulValue(DomAutoCompleteValueState.NONE);
		} else if (input.getValidationMode().isPermissive()) {
			// TODO PLAT-753 This cast should not be necessary. Permissive mode should not even be handled in the same auto-complete input implementation.
			return createStatefulValue(CastUtils.<T> cast(input.getValueText()));
		} else {
			var matches = input.getInputEngine().findMatches(input.getPattern(), 2);
			if (matches.isEmpty()) {
				return createStatefulValue(DomAutoCompleteValueState.ILLEGAL);
			} else {
				if (matches.size() == 1) {
					return createStatefulValue(matches.getAll().iterator().next().getValue());
				} else if (matches.getPerfectMatchValue().isPresent()) {
					return createStatefulValue(matches.getPerfectMatchValue().get());
				} else {
					return createStatefulValue(DomAutoCompleteValueState.AMBIGUOUS);
				}
			}
		}
	}

	private DomAutoCompleteStatefulValue<T> createStatefulValue(DomAutoCompleteValueState state) {

		return new DomAutoCompleteStatefulValue<>(null, state);
	}

	private DomAutoCompleteStatefulValue<T> createStatefulValue(T value) {

		return new DomAutoCompleteStatefulValue<>(value, DomAutoCompleteValueState.VALID);
	}
}
