package com.softicar.platform.dom.input.auto;

/**
 * Enumerates the available indicator display modes for
 * {@link IDomAutoCompleteInput}.
 * <p>
 * Note: The entries and IDs in this enum must reflect those in its JavaScript
 * counterpart.
 *
 * @author Alexander Schmidt
 */
public enum DomAutoCompleteInputIndicatorMode {

	/**
	 * In {@link DomAutoCompleteInputIndicatorMode#GENERIC} mode, the
	 * auto-complete input element displays a "bulb" indicator while not
	 * communicating with the server.
	 */
	GENERIC(1),

	/**
	 * In {@link DomAutoCompleteInputIndicatorMode#VALIDATION} mode, the
	 * auto-complete input element displays various indicators that reflect the
	 * validation state of the entered pattern.
	 */
	VALIDATION(2);

	private final int id;

	private DomAutoCompleteInputIndicatorMode(int id) {

		this.id = id;
	}

	public int getId() {

		return id;
	}

	/**
	 * @return true if the mode is {@link #GENERIC}. false otherwise.
	 */
	public boolean isGeneric() {

		return this == GENERIC;
	}

	/**
	 * @return true if the mode is {@link #VALIDATION}. false otherwise.
	 */
	public boolean isValidation() {

		return this == VALIDATION;
	}
}
