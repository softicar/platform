package com.softicar.platform.db.runtime.structure.comparison.strategy;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.structure.comparison.column.DbColumnStructureCompatibilityComparer;
import com.softicar.platform.db.runtime.structure.comparison.enumeration.DbEnumTableRowStructureCompatibilityComparer;
import com.softicar.platform.db.runtime.structure.comparison.feature.IDbFeatureStructureComparer;
import com.softicar.platform.db.runtime.structure.comparison.foreign.key.DbForeignKeyStructureCompatibilityComparer;
import com.softicar.platform.db.runtime.structure.comparison.key.DbKeyStructureCompatibilityComparer;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;

public class DbStructureCompatibilityComparisonStrategy implements IDbStructureComparisonStrategy {

	@Override
	public IDbFeatureStructureComparer getColumnComparer(IDbTableStructureComparerConfiguration configuration) {

		return new DbColumnStructureCompatibilityComparer(configuration);
	}

	@Override
	public IDbFeatureStructureComparer getForeignKeyComparer(IDbTableStructureComparerConfiguration configuration) {

		return new DbForeignKeyStructureCompatibilityComparer(configuration);
	}

	@Override
	public IDbFeatureStructureComparer getKeyComparer(IDbTableStructureComparerConfiguration configuration) {

		return new DbKeyStructureCompatibilityComparer(configuration);
	}

	@Override
	public IDbFeatureStructureComparer getEnumTableRowComparer(IDbTableStructureComparerConfiguration configuration) {

		return new DbEnumTableRowStructureCompatibilityComparer(configuration);
	}

	@Override
	public IDisplayString getReferenceName() {

		return IDisplayString.create("Reference");
	}

	@Override
	public IDisplayString getSampleName() {

		return IDisplayString.create("Sample");
	}
}
