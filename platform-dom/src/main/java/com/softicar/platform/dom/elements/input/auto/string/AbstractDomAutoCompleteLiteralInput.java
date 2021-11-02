package com.softicar.platform.dom.elements.input.auto.string;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import java.util.Optional;

public abstract class AbstractDomAutoCompleteLiteralInput<T> extends DomAutoCompleteInput<T> implements IDomAutoCompleteLiteralInput {

	public AbstractDomAutoCompleteLiteralInput(IDomAutoCompleteInputEngine<T> inputEngine) {

		super(inputEngine, false, DomAutoCompleteInputValidationMode.RESTRICTIVE);
	}

	protected abstract Optional<String> getValueFromSelection();

	@Override
	public Optional<String> getValueString() {

		Optional<String> valueString;
		if (getConfiguration().getValidationMode().isPermissive()) {
			valueString = Optional.of(getRawValueString());
		} else {
			valueString = getValueFromSelection();
		}
		return valueString//
			.map(String::trim)
			.filter(it -> !it.isEmpty());
	}

	@Override
	public String getValueStringOrNull() {

		return getValueString().orElse(null);
	}

	@Override
	public String getValueStringOrEmpty() {

		return getValueString().orElse("");
	}

	@Override
	public void clearValueString() {

		setValueString(null);
	}

	@Override
	public boolean isEmptyValueString() {

		return !getValueString().isPresent();
	}

	// deliberately increase visibility of base class method
	@Override
	public String getRawValueString() {

		return super.getRawValueString();
	}
}
