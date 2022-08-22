package com.softicar.platform.dom.input.auto;

/**
 * Enumerates the available pattern validation modes for
 * {@link IDomAutoCompleteInput}.
 *
 * @author Alexander Schmidt
 */
public enum DomAutoCompleteInputValidationMode {

	/**
	 * In {@link DomAutoCompleteInputValidationMode#DEDUCTIVE} mode, the input
	 * element performs pattern-to-value deduction.
	 * <p>
	 * Example: Let ["foo", "bar", "baz"] be all of the values which the input
	 * element can offer for selection. Then, entered patterns yield the
	 * following deduced values:
	 *
	 * <pre>
	 * Pattern | Values
	 * --------+---------------
	 * "a"     | ["bar", "baz"]
	 * "ba"    | ["bar", "baz"]
	 * "BA"    | ["bar", "baz"]
	 * "fo"    | ["foo"]
	 * "foo"   | ["foo"]
	 * "x"     | []
	 * "z"     | ["baz"]
	 * </pre>
	 *
	 * Deduced values are accessible via
	 * {@link IDomAutoCompleteInput#getSelection()}.
	 */
	DEDUCTIVE,

	/**
	 * In {@link DomAutoCompleteInputValidationMode#PERMISSIVE} mode, the input
	 * element indicates <i>any</i> entered pattern to be valid. This is
	 * particularly useful for String based input elements in which the
	 * auto-complete filter result items are mere suggestions.
	 * <p>
	 * Note that, even when the input element is permissive,
	 * {@link IDomAutoCompleteInput#getSelection()} only provides values that
	 * are available as auto-complete filter results. Hence, the raw value
	 * string must be obtained and interpreted, if necessary.
	 */
	PERMISSIVE;

	/**
	 * @return true if the mode is {@link #DEDUCTIVE}. false otherwise.
	 */
	public boolean isDeductive() {

		return this == DEDUCTIVE;
	}

	/**
	 * @return true if the mode is {@link #PERMISSIVE}. false otherwise.
	 */
	public boolean isPermissive() {

		return this == PERMISSIVE;
	}
}
