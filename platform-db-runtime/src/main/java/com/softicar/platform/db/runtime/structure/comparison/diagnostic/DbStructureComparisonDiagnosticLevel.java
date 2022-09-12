package com.softicar.platform.db.runtime.structure.comparison.diagnostic;

/**
 * Enumerates existing levels of diagnostics about database structures.
 *
 * @author Alexander Schmidt
 */
public enum DbStructureComparisonDiagnosticLevel {

	/**
	 * Indicates a critical structural difference.
	 */
	ERROR("ERRORS"),

	/**
	 * Indicates a potentially problematic structural difference.
	 */
	WARNING("WARNINGS"),

	/**
	 * Indicates an undesirable but not directly problematic structural
	 * difference.
	 */
	INFO("INFOS");

	private final String pluralTitle;

	private DbStructureComparisonDiagnosticLevel(String pluralTitle) {

		this.pluralTitle = pluralTitle;
	}

	public String getPluralTitle() {

		return pluralTitle;
	}
}
