package com.softicar.platform.db.runtime.structure.comparison.feature;

import com.softicar.platform.db.runtime.structure.comparison.diagnostic.DbStructureComparisonDiagnosticContainer;

public interface IDbFeatureStructureComparer {

	DbStructureComparisonDiagnosticContainer compare();
}
