package com.softicar.platform.db.runtime.structure.comparison.key;

import com.softicar.platform.db.runtime.structure.comparison.feature.checker.DbExclusiveFeatureChecker;
import com.softicar.platform.db.runtime.structure.comparison.feature.checker.DbMutualFeatureChecker;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.key.IDbKeyStructure;

public class DbKeyStructureCompatibilityComparer extends AbstractDbKeyStructureComparer {

	public DbKeyStructureCompatibilityComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration);
	}

	@Override
	protected void checkMutualFeature(DbMutualFeatureChecker<IDbKeyStructure> checker) {

		checker//
			.compare("Key Type", IDbKeyStructure::getType)
			.addWarningIf(Values::notEqual)

			.compare("Column List", this::getColumnNames)
			.addWarningIf(Values::notEqual);
	}

	@Override
	protected void checkReferenceExclusiveFeature(DbExclusiveFeatureChecker<IDbKeyStructure> checker) {

		checker//
			.addInfoIf(this::isIndexKey, "Surplus index in %s.", getReferenceName())
			.addWarningIf(this::isUniqueKey, "Surplus unique key in %s.", getReferenceName())
			.addWarningIf(this::isPrimaryKey, "Surplus primary key in %s.", getReferenceName());
	}

	@Override
	protected void checkSampleExclusiveFeature(DbExclusiveFeatureChecker<IDbKeyStructure> checker) {

		checker//
			.addInfoIf(this::isIndexKey, "Surplus index in %s.", getSampleName())
			.addWarningIf(this::isUniqueKey, "Surplus unique key in %s.", getSampleName())
			.addWarningIf(this::isPrimaryKey, "Surplus primary key in %s.", getSampleName());
	}
}
