package com.softicar.platform.dom.input.auto;

/**
 * Configuration for an {@link IDomAutoCompleteInput} input element.
 *
 * @author Alexander Schmidt
 */
public interface IDomAutoCompleteInputConfiguration {

	/**
	 * @return the {@link DomAutoCompleteInputValidationMode} of this input
	 *         element (never null)
	 * @see #setValidationMode(DomAutoCompleteInputValidationMode)
	 * @see #setValidationModeDeductive()
	 * @see #setValidationModePermissive()
	 */
	DomAutoCompleteInputValidationMode getValidationMode();

	/**
	 * Sets the pattern validation mode for the input element.
	 *
	 * @param validationMode
	 *            the mode to be set (never null)
	 */
	IDomAutoCompleteInputConfiguration setValidationMode(DomAutoCompleteInputValidationMode validationMode);

	/**
	 * Sets the pattern validation mode to
	 * {@link DomAutoCompleteInputValidationMode#DEDUCTIVE}.
	 */
	default IDomAutoCompleteInputConfiguration setValidationModeDeductive() {

		return setValidationMode(DomAutoCompleteInputValidationMode.DEDUCTIVE);
	}

	/**
	 * Sets the pattern validation mode to
	 * {@link DomAutoCompleteInputValidationMode#PERMISSIVE}.
	 */
	default IDomAutoCompleteInputConfiguration setValidationModePermissive() {

		return setValidationMode(DomAutoCompleteInputValidationMode.PERMISSIVE);
	}
}
