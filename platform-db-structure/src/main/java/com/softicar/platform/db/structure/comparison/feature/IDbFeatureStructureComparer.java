package com.softicar.platform.db.structure.comparison.feature;

import com.softicar.platform.db.structure.comparison.diagnostic.DbStructureComparisonDiagnosticContainer;

public interface IDbFeatureStructureComparer {

	DbStructureComparisonDiagnosticContainer compare();
}
