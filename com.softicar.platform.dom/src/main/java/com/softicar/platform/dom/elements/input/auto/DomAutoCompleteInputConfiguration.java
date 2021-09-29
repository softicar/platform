package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.input.IDomEnableable;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputIndicatorMode;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInput;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInputConfiguration;
import java.util.Objects;

public class DomAutoCompleteInputConfiguration implements IDomAutoCompleteInputConfiguration {

	private final IDomAutoCompleteInput<?> input;
	private final IDomEnableable inputField;
	private DomAutoCompleteInputIndicatorMode indicatorMode;
	private DomAutoCompleteInputValidationMode validationMode;
	private boolean mandatory;
	private boolean enabled;

	public DomAutoCompleteInputConfiguration(IDomAutoCompleteInput<?> input, IDomEnableable inputField) {

		this.input = input;
		this.inputField = inputField;
		this.validationMode = DomAutoCompleteInputValidationMode.DEDUCTIVE;
		this.indicatorMode = DomAutoCompleteInputIndicatorMode.VALIDATION;
		this.mandatory = false;
		this.enabled = true;
	}

	@Override
	public DomAutoCompleteInputIndicatorMode getIndicatorMode() {

		return indicatorMode;
	}

	@Override
	public IDomAutoCompleteInputConfiguration setIndicatorMode(DomAutoCompleteInputIndicatorMode indicatorMode) {

		Objects.requireNonNull(indicatorMode);
		getDomEngine().setAutoCompleteIndicatorMode(input, indicatorMode);
		this.indicatorMode = indicatorMode;
		return this;
	}

	@Override
	public DomAutoCompleteInputValidationMode getValidationMode() {

		return validationMode;
	}

	@Override
	public IDomAutoCompleteInputConfiguration setValidationMode(DomAutoCompleteInputValidationMode validationMode) {

		Objects.requireNonNull(validationMode);
		getDomEngine().setAutoCompleteValidationMode(input, validationMode);
		this.validationMode = validationMode;
		return this;
	}

	@Override
	public boolean isMandatory() {

		return mandatory;
	}

	@Override
	public IDomAutoCompleteInputConfiguration setMandatory(boolean mandatory) {

		getDomEngine().setAutoCompleteInputMandatory(input, mandatory);
		this.mandatory = mandatory;
		return this;
	}

	@Override
	public boolean isEnabled() {

		return enabled;
	}

	@Override
	public IDomAutoCompleteInputConfiguration setEnabled(boolean enabled) {

		getDomEngine().setAutoCompleteEnabled(input, enabled);
		this.inputField.setEnabled(enabled);
		this.enabled = enabled;
		return this;
	}

	private IDomEngine getDomEngine() {

		return this.input.getDomEngine();
	}
}
