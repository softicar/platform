package com.softicar.platform.db.structure.comparison.strategy;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.structure.comparison.feature.IDbFeatureStructureComparer;
import com.softicar.platform.db.structure.comparison.table.IDbTableStructureComparerConfiguration;

public interface IDbStructureComparisonStrategy {

	IDbFeatureStructureComparer getColumnComparer(IDbTableStructureComparerConfiguration configuration);

	IDbFeatureStructureComparer getForeignKeyComparer(IDbTableStructureComparerConfiguration configuration);

	IDbFeatureStructureComparer getKeyComparer(IDbTableStructureComparerConfiguration configuration);

	IDbFeatureStructureComparer getEnumTableRowComparer(IDbTableStructureComparerConfiguration configuration);

	default IDisplayString getReferenceName() {

		return IDisplayString.create("Reference");
	}

	default IDisplayString getSampleName() {

		return IDisplayString.create("Sample");
	}
}
