package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInputConfiguration;
import java.util.Objects;

public class DomAutoCompleteInputConfiguration implements IDomAutoCompleteInputConfiguration {

	private final DomAutoCompleteInput<?> input;
	private DomAutoCompleteInputValidationMode validationMode;

	public DomAutoCompleteInputConfiguration(DomAutoCompleteInput<?> input, DomAutoCompleteInputValidationMode validationMode) {

		this.input = input;
		this.validationMode = validationMode;
	}

	@Override
	public DomAutoCompleteInputValidationMode getValidationMode() {

		return validationMode;
	}

	@Override
	public IDomAutoCompleteInputConfiguration setValidationMode(DomAutoCompleteInputValidationMode validationMode) {

		Objects.requireNonNull(validationMode);
		this.validationMode = validationMode;
		input.refreshIndicator();
		return this;
	}
}
