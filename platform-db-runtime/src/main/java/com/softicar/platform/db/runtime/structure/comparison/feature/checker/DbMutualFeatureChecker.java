package com.softicar.platform.db.runtime.structure.comparison.feature.checker;

import com.softicar.platform.db.runtime.structure.comparison.diagnostic.DbStructureComparisonDiagnostic;
import com.softicar.platform.db.runtime.structure.comparison.diagnostic.DbStructureComparisonDiagnosticBuilder;
import com.softicar.platform.db.runtime.structure.comparison.diagnostic.DbStructureComparisonDiagnosticLevel;
import com.softicar.platform.db.runtime.structure.comparison.feature.AbstractDbFeatureStructureComparer;
import com.softicar.platform.db.runtime.structure.comparison.helper.DbTableStructureFeatureIdentifier;
import com.softicar.platform.db.structure.table.IDbTableStructureFeature;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class DbMutualFeatureChecker<F extends IDbTableStructureFeature> {

	private final AbstractDbFeatureStructureComparer<F> comparer;
	private final DbTableStructureFeatureIdentifier identifier;
	private final F referenceFeature;
	private final F sampleFeature;

	public DbMutualFeatureChecker(AbstractDbFeatureStructureComparer<F> comparer, DbTableStructureFeatureIdentifier identifier, F referenceFeature,
			F sampleFeature) {

		this.comparer = comparer;
		this.identifier = identifier;
		this.referenceFeature = referenceFeature;
		this.sampleFeature = sampleFeature;
	}

	public <T> Adder<T> compare(String propertyName, Function<F, T> function) {

		return compare(propertyName, function, Objects::toString);
	}

	public <T> Adder<T> compare(String propertyName, Function<F, T> function, Function<T, String> valueToString) {

		return new Adder<>(propertyName, function, valueToString);
	}

	public class Adder<T> {

		private final String propertyName;
		private final Function<F, T> function;
		private final Function<T, String> valueToString;

		/**
		 * @param propertyName
		 *            the name of the checked property (never null)
		 * @param function
		 *            a getter for the property value (never null)
		 * @param valueToString
		 *            a function to transform a value into a String (never null)
		 */
		public Adder(String propertyName, Function<F, T> function, Function<T, String> valueToString) {

			this.propertyName = propertyName;
			this.function = function;
			this.valueToString = valueToString;
		}

		public Adder<T> addErrorIf(BiPredicate<T, T> predicate) {

			return addDiagnosticIf(DbStructureComparisonDiagnosticLevel.ERROR, predicate);
		}

		public Adder<T> addWarningIf(BiPredicate<T, T> predicate) {

			return addDiagnosticIf(DbStructureComparisonDiagnosticLevel.WARNING, predicate);
		}

		public Adder<T> addInfoIf(BiPredicate<T, T> predicate) {

			return addDiagnosticIf(DbStructureComparisonDiagnosticLevel.INFO, predicate);
		}

		/**
		 * Adds a diagnostic if the given predicate evaluates to true.
		 * <p>
		 * The first value given to the predicate is from the reference
		 * structure. The second value is from the sample structure.
		 *
		 * @param level
		 *            the level of the diagnostic to add
		 * @param predicate
		 *            the predicate to evaluate
		 */
		public Adder<T> addDiagnosticIf(DbStructureComparisonDiagnosticLevel level, BiPredicate<T, T> predicate) {

			T referenceValue = function.apply(referenceFeature);
			T sampleValue = function.apply(sampleFeature);
			if (predicate.test(referenceValue, sampleValue)) {
				DbStructureComparisonDiagnostic diagnostic = new DbStructureComparisonDiagnosticBuilder(comparer.getFeatureType())//
					.setLevel(level)
					.setTableStructures(comparer.getReferenceStructure(), comparer.getSampleStructure())
					.build(//
						"%s: %s: '%s' vs. %s: '%s'",
						propertyName,
						comparer.getReferenceName(),
						valueToString.apply(referenceValue),
						comparer.getSampleName(),
						valueToString.apply(sampleValue));
				diagnostic.setIdentifier(identifier);
				comparer.addDiagnostic(diagnostic);
			}
			return this;
		}

		public <X> Adder<X> compare(String propertyName, Function<F, X> function) {

			return DbMutualFeatureChecker.this.compare(propertyName, function);
		}
	}
}
