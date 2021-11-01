package com.softicar.platform.dom.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * Enumerates the available pattern validation modes for
 * {@link IDomAutoCompleteInput}.
 * <p>
 * Note: The entries and IDs in this enum must reflect those in its JavaScript
 * counterpart.
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
	DEDUCTIVE(1),

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
	PERMISSIVE(2),

	/**
	 * In {@link DomAutoCompleteInputValidationMode#RESTRICTIVE} mode, the input
	 * element only indicates an entered pattern to be valid if there is a
	 * filter result value with the exact same {@link IDisplayString} (case
	 * sensitive).
	 * <p>
	 * Valid values can be retrieved via
	 * {@link IDomAutoCompleteInput#getSelection()}.
	 */
	RESTRICTIVE(3);

	private final int id;

	private DomAutoCompleteInputValidationMode(int id) {

		this.id = id;
	}

	public int getId() {

		return id;
	}

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

	/**
	 * @return true if the mode is {@link #RESTRICTIVE}. false otherwise.
	 */
	public boolean isRestrictive() {

		return this == RESTRICTIVE;
	}
}
