package com.softicar.platform.dom.input.auto;

/**
 * Configuration for an {@link IDomAutoCompleteInput} input element.
 *
 * @author Alexander Schmidt
 */
public interface IDomAutoCompleteInputConfiguration {

	/**
	 * @return the {@link DomAutoCompleteInputIndicatorMode} of this input
	 *         element (never null)
	 * @see #setIndicatorMode(DomAutoCompleteInputIndicatorMode)
	 * @see #setIndicatorModeGeneric()
	 * @see #setIndicatorModeValidation()
	 */
	DomAutoCompleteInputIndicatorMode getIndicatorMode();

	/**
	 * Sets the indicator display mode for the input element.
	 *
	 * @param indicatorMode
	 *            the mode to be set (never null)
	 */
	IDomAutoCompleteInputConfiguration setIndicatorMode(DomAutoCompleteInputIndicatorMode indicatorMode);

	/**
	 * Sets the indicator display mode to
	 * {@link DomAutoCompleteInputIndicatorMode#GENERIC}.
	 */
	default IDomAutoCompleteInputConfiguration setIndicatorModeGeneric() {

		return setIndicatorMode(DomAutoCompleteInputIndicatorMode.GENERIC);
	}

	/**
	 * Sets the indicator display mode to
	 * {@link DomAutoCompleteInputIndicatorMode#VALIDATION}.
	 */
	default IDomAutoCompleteInputConfiguration setIndicatorModeValidation() {

		return setIndicatorMode(DomAutoCompleteInputIndicatorMode.VALIDATION);
	}

	/**
	 * @return the {@link DomAutoCompleteInputValidationMode} of this input
	 *         element (never null)
	 * @see #setValidationMode(DomAutoCompleteInputValidationMode)
	 * @see #setValidationModeDeductive()
	 * @see #setValidationModePermissive()
	 * @see #setValidationModeRestrictive()
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

	/**
	 * Sets the pattern validation mode to
	 * {@link DomAutoCompleteInputValidationMode#RESTRICTIVE}.
	 */
	default IDomAutoCompleteInputConfiguration setValidationModeRestrictive() {

		return setValidationMode(DomAutoCompleteInputValidationMode.RESTRICTIVE);
	}

	/**
	 * @return true if the input element is flagged as mandatory. false
	 *         otherwise.
	 * @see #setMandatory(boolean)
	 */
	boolean isMandatory();

	/**
	 * Flags the input element as mandatory. When a mandatory input element is
	 * empty, it indicates that a value is missing.
	 *
	 * @param mandatory
	 *            true if input should be mandatory. false otherwise.
	 */
	IDomAutoCompleteInputConfiguration setMandatory(boolean mandatory);

	/**
	 * @return true if the input element is enabled. false otherwise.
	 */
	boolean isEnabled();

	/**
	 * Toggles the enabled state of the input element.
	 *
	 * @param enabled
	 *            true if the input element should be enabled. false otherwise.
	 */
	IDomAutoCompleteInputConfiguration setEnabled(boolean enabled);
}
