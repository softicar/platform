package com.softicar.platform.db.structure.comparison.feature;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.structure.comparison.diagnostic.DbStructureComparisonDiagnostic;
import com.softicar.platform.db.structure.comparison.diagnostic.DbStructureComparisonDiagnosticContainer;
import com.softicar.platform.db.structure.comparison.feature.checker.DbExclusiveFeatureChecker;
import com.softicar.platform.db.structure.comparison.feature.checker.DbMutualFeatureChecker;
import com.softicar.platform.db.structure.comparison.helper.DbTableStructureFeatureIdentifier;
import com.softicar.platform.db.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.table.DbTableStructureFeatureType;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.table.IDbTableStructureFeature;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import org.apache.commons.collections4.SetUtils;

/**
 * Processes structure features to validate compatibility, applying a specific
 * strategy.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractDbFeatureStructureComparer<F extends IDbTableStructureFeature> implements IDbFeatureStructureComparer {

	private final IDbTableStructureComparerConfiguration configuration;
	private final DbTableStructureFeatureType featureType;
	private final Map<DbTableStructureFeatureIdentifier, F> referenceMap;
	private final Map<DbTableStructureFeatureIdentifier, F> sampleMap;
	private final DbStructureComparisonDiagnosticContainer diagnostics;

	public AbstractDbFeatureStructureComparer(IDbTableStructureComparerConfiguration configuration, DbTableStructureFeatureType featureType) {

		this.configuration = configuration;
		this.featureType = featureType;
		this.referenceMap = new FeatureMap(configuration.getReferenceStructure());
		this.sampleMap = new FeatureMap(configuration.getSampleStructure());
		this.diagnostics = new DbStructureComparisonDiagnosticContainer();
	}

	@Override
	public DbStructureComparisonDiagnosticContainer compare() {

		this.diagnostics.clear();
		checkMutualFeatures();
		checkReferenceExclusiveFeatures();
		checkSampleExclusiveFeatures();
		return diagnostics;
	}

	public DbTableStructureFeatureType getFeatureType() {

		return featureType;
	}

	public IDbTableStructure getReferenceStructure() {

		return configuration.getReferenceStructure();
	}

	public IDbTableStructure getSampleStructure() {

		return configuration.getSampleStructure();
	}

	public IDisplayString getReferenceName() {

		return configuration.getReferenceName();
	}

	public IDisplayString getSampleName() {

		return configuration.getSampleName();
	}

	public void addDiagnostic(DbStructureComparisonDiagnostic diagnostic) {

		diagnostics.add(diagnostic);
	}

	// ------------------------------ protected abstract ------------------------------ //

	/**
	 * Gets an identifier for the given structure feature. Features with equal
	 * identifiers are assumed to correspond to each other, and therefore
	 * compared with each other.
	 *
	 * @param feature
	 *            the feature for which an identifier shall be determined (never
	 *            null)
	 * @return the identifier for the given feature (never null)
	 */
	protected abstract DbTableStructureFeatureIdentifier getIdentifier(F feature);

	/**
	 * Fetches the structure features from the given {@link IDbTableStructure}.
	 *
	 * @param tableStructure
	 *            the table structure from which features shall be fetched
	 *            (never null)
	 * @return a {@link Collection} of features from the given
	 *         {@link IDbTableStructure} (never null)
	 */
	protected abstract Collection<? extends F> getStructureFeatures(IDbTableStructure tableStructure);

	/**
	 * Checks corresponding structure features (which exist in both the
	 * reference and the sample).
	 *
	 * @param checker
	 *            the {@link DbMutualFeatureChecker} to use
	 */
	protected abstract void checkMutualFeature(DbMutualFeatureChecker<F> checker);

	/**
	 * Checks structure features that only exist in the reference (without a
	 * corresponding feature in the sample).
	 *
	 * @param checker
	 *            the {@link DbExclusiveFeatureChecker} to use
	 */
	protected abstract void checkReferenceExclusiveFeature(DbExclusiveFeatureChecker<F> checker);

	/**
	 * Checks structure features that only exist in the sample (without a
	 * corresponding feature in the reference).
	 *
	 * @param checker
	 *            the {@link DbExclusiveFeatureChecker} to use
	 */
	protected abstract void checkSampleExclusiveFeature(DbExclusiveFeatureChecker<F> checker);

	// ------------------------------ private ------------------------------ //

	private void checkMutualFeatures() {

		for (DbTableStructureFeatureIdentifier identifier: SetUtils.intersection(referenceMap.keySet(), sampleMap.keySet())) {
			F referenceFeature = referenceMap.get(identifier);
			F sampleFeature = sampleMap.get(identifier);
			checkMutualFeature(new DbMutualFeatureChecker<>(this, identifier, referenceFeature, sampleFeature));
		}
	}

	private void checkReferenceExclusiveFeatures() {

		for (DbTableStructureFeatureIdentifier identifier: SetUtils.difference(referenceMap.keySet(), sampleMap.keySet())) {
			F referenceFeature = referenceMap.get(identifier);
			checkReferenceExclusiveFeature(new DbExclusiveFeatureChecker<>(this, identifier, referenceFeature));
		}
	}

	private void checkSampleExclusiveFeatures() {

		for (DbTableStructureFeatureIdentifier identifier: SetUtils.difference(sampleMap.keySet(), referenceMap.keySet())) {
			F sampleFeature = sampleMap.get(identifier);
			checkSampleExclusiveFeature(new DbExclusiveFeatureChecker<>(this, identifier, sampleFeature));
		}
	}

	private class FeatureMap extends TreeMap<DbTableStructureFeatureIdentifier, F> {

		public FeatureMap(IDbTableStructure tableStructure) {

			getStructureFeatures(tableStructure).forEach(this::put);
		}

		private void put(F feature) {

			put(getIdentifier(feature), feature);
		}
	}

	// ------------------------------ protected sub-classes ------------------------------ //

	protected interface Booleans {

		static boolean onlyReference(boolean reference, boolean sample) {

			return reference && !sample;
		}

		static boolean onlySample(boolean reference, boolean sample) {

			return !reference && sample;
		}
	}

	protected interface Integers {

		static boolean referenceSmaller(Integer reference, Integer sample) {

			return Comparator.nullsFirst(Integer::compareTo).compare(reference, sample) < 0;
		}

		static boolean referenceGreater(Integer reference, Integer sample) {

			return Comparator.nullsFirst(Integer::compareTo).compare(reference, sample) > 0;
		}
	}

	protected interface Values {

		static boolean notEqual(Object reference, Object sample) {

			return !Objects.equals(reference, sample);
		}

		static <O> boolean notEqual(Optional<O> reference, Optional<O> sample) {

			if (reference.isPresent() && sample.isPresent()) {
				return !Objects.equals(reference, sample);
			} else if (!reference.isPresent() && !sample.isPresent()) {
				return false;
			} else {
				return true;
			}
		}
	}
}
