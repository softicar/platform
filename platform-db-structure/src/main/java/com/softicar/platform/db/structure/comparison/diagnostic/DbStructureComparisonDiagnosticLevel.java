package com.softicar.platform.db.structure.comparison.diagnostic;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;

/**
 * Enumerates existing levels of diagnostics about database structures.
 *
 * @author Alexander Schmidt
 */
public enum DbStructureComparisonDiagnosticLevel implements IDisplayable {

	/**
	 * Indicates a critical structural difference.
	 */
	ERROR(DbStructureComparisonDiagnosticI18n.ERROR),

	/**
	 * Indicates a potentially problematic structural difference.
	 */
	WARNING(DbStructureComparisonDiagnosticI18n.WARNING),

	/**
	 * Indicates an undesirable but not directly problematic structural
	 * difference.
	 */
	INFO(DbStructureComparisonDiagnosticI18n.INFO);

	private final IDisplayString label;

	private DbStructureComparisonDiagnosticLevel(IDisplayString label) {

		this.label = label;
	}

	@Override
	public IDisplayString toDisplay() {

		return label;
	}
}
