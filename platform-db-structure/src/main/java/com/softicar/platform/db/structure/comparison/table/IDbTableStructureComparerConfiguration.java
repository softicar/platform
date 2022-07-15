package com.softicar.platform.db.structure.comparison.table;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.structure.comparison.feature.IDbFeatureStructureComparer;
import com.softicar.platform.db.structure.comparison.strategy.IDbStructureComparisonStrategy;
import com.softicar.platform.db.structure.table.IDbTableStructure;

/**
 * The configuration of {@link DbTableStructureComparer}.
 *
 * @author Alexander Schmidt
 */
public interface IDbTableStructureComparerConfiguration {

	/**
	 * @return the strategy to apply (never null)
	 */
	IDbStructureComparisonStrategy getStrategy();

	/**
	 * @return the reference structure to compare to the sample structure (never
	 *         null)
	 */
	IDbTableStructure getReferenceStructure();

	/**
	 * @return the sample structure to compare to the reference structure (never
	 *         null)
	 */
	IDbTableStructure getSampleStructure();

	default IDbFeatureStructureComparer getColumnComparer() {

		return getStrategy().getColumnComparer(this);
	}

	default IDbFeatureStructureComparer getForeignKeyComparer() {

		return getStrategy().getForeignKeyComparer(this);
	}

	default IDbFeatureStructureComparer getKeyComparer() {

		return getStrategy().getKeyComparer(this);
	}

	default IDbFeatureStructureComparer getEnumTableRowComparer() {

		return getStrategy().getEnumTableRowComparer(this);
	}

	default IDisplayString getReferenceName() {

		return getStrategy().getReferenceName();
	}

	default IDisplayString getSampleName() {

		return getStrategy().getSampleName();
	}
}
