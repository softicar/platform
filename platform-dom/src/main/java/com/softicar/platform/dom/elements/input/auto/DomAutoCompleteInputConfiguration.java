package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.input.IDomInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInputConfiguration;
import java.util.Objects;

public class DomAutoCompleteInputConfiguration implements IDomAutoCompleteInputConfiguration {

	private final DomAutoCompleteInput<?> input;
	private final IDomInput inputField;
	private DomAutoCompleteInputValidationMode validationMode;
	private boolean mandatory;
	private boolean disabled;

	public DomAutoCompleteInputConfiguration(DomAutoCompleteInput<?> input, IDomInput inputField, DomAutoCompleteInputValidationMode validationMode) {

		this.input = input;
		this.inputField = inputField;
		this.validationMode = validationMode;
		this.mandatory = false;
		this.disabled = false;
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

	@Override
	public boolean isMandatory() {

		return mandatory;
	}

	@Override
	public IDomAutoCompleteInputConfiguration setMandatory(boolean mandatory) {

		this.mandatory = mandatory;
		input.refreshIndicator();
		return this;
	}

	@Override
	public boolean isDisabled() {

		return disabled;
	}

	@Override
	public IDomAutoCompleteInputConfiguration setDisabled(boolean disabled) {

		this.inputField.setDisabled(disabled);
		this.disabled = disabled;
		return this;
	}
}
