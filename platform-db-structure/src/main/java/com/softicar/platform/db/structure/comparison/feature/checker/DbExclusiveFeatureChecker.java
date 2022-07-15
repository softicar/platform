package com.softicar.platform.db.structure.comparison.feature.checker;

import com.softicar.platform.db.structure.comparison.diagnostic.DbStructureComparisonDiagnostic;
import com.softicar.platform.db.structure.comparison.diagnostic.DbStructureComparisonDiagnosticBuilder;
import com.softicar.platform.db.structure.comparison.diagnostic.DbStructureComparisonDiagnosticLevel;
import com.softicar.platform.db.structure.comparison.feature.AbstractDbFeatureStructureComparer;
import com.softicar.platform.db.structure.comparison.helper.DbTableStructureFeatureIdentifier;
import com.softicar.platform.db.structure.table.IDbTableStructureFeature;
import java.util.function.Predicate;

public class DbExclusiveFeatureChecker<F extends IDbTableStructureFeature> {

	private final AbstractDbFeatureStructureComparer<F> comparer;
	private final DbTableStructureFeatureIdentifier identifier;
	private final F feature;

	public DbExclusiveFeatureChecker(AbstractDbFeatureStructureComparer<F> comparer, DbTableStructureFeatureIdentifier identifier, F feature) {

		this.comparer = comparer;
		this.identifier = identifier;
		this.feature = feature;
	}

	public F getFeature() {

		return feature;
	}

	public DbExclusiveFeatureChecker<F> addErrorIf(Predicate<F> predicate, String text, Object...args) {

		return addDiagnosticIf(DbStructureComparisonDiagnosticLevel.ERROR, predicate, text, args);
	}

	public DbExclusiveFeatureChecker<F> addWarningIf(Predicate<F> predicate, String text, Object...args) {

		return addDiagnosticIf(DbStructureComparisonDiagnosticLevel.WARNING, predicate, text, args);
	}

	public DbExclusiveFeatureChecker<F> addInfoIf(Predicate<F> predicate, String text, Object...args) {

		return addDiagnosticIf(DbStructureComparisonDiagnosticLevel.INFO, predicate, text, args);
	}

	public DbExclusiveFeatureChecker<F> addDiagnosticIf(DbStructureComparisonDiagnosticLevel level, Predicate<F> predicate, String text, Object...args) {

		if (predicate.test(feature)) {
			DbStructureComparisonDiagnostic diagnostic = new DbStructureComparisonDiagnosticBuilder(comparer.getFeatureType())//
				.setLevel(level)
				.setTableStructures(comparer.getReferenceStructure(), comparer.getSampleStructure())
				.build(text, args);
			diagnostic.setIdentifier(identifier);
			comparer.addDiagnostic(diagnostic);
		}
		return this;
	}
}
