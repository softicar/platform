package com.softicar.platform.dom.elements.input.auto;

class DomAutoCompleteValueAndState<T> {

	private final T value;
	private final DomAutoCompleteValueState state;

	public DomAutoCompleteValueAndState(T value, DomAutoCompleteValueState state) {

		this.value = value;
		this.state = state;
	}

	public T getValue() {

		return value;
	}

	public DomAutoCompleteValueState getState() {

		return state;
	}

	public boolean isAmbiguous() {

		return state == DomAutoCompleteValueState.AMBIGUOUS;
	}

	public boolean isIllegal() {

		return state == DomAutoCompleteValueState.ILLEGAL;
	}

	public boolean isValid() {

		return state == DomAutoCompleteValueState.VALID;
	}

	public boolean isAmbiguousOrIllegal() {

		return isAmbiguous() || isIllegal();
	}
}
