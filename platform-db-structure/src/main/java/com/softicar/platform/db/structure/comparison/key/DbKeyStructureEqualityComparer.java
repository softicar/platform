package com.softicar.platform.db.structure.comparison.key;

import com.softicar.platform.db.structure.comparison.feature.checker.DbExclusiveFeatureChecker;
import com.softicar.platform.db.structure.comparison.feature.checker.DbMutualFeatureChecker;
import com.softicar.platform.db.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.key.IDbKeyStructure;

public class DbKeyStructureEqualityComparer extends AbstractDbKeyStructureComparer {

	public DbKeyStructureEqualityComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration);
	}

	@Override
	protected void checkMutualFeature(DbMutualFeatureChecker<IDbKeyStructure> checker) {

		checker//
			.compare("Key Type", IDbKeyStructure::getType)
			.addErrorIf(Values::notEqual)

			.compare("Column List", this::getColumnNames)
			.addErrorIf(Values::notEqual);
	}

	@Override
	protected void checkReferenceExclusiveFeature(DbExclusiveFeatureChecker<IDbKeyStructure> checker) {

		checker//
			.addErrorIf(this::isIndexKey, "Surplus index in %s.", getReferenceName())
			.addErrorIf(this::isUniqueKey, "Surplus unique key in %s.", getReferenceName())
			.addErrorIf(this::isPrimaryKey, "Surplus primary key in %s.", getReferenceName());
	}

	@Override
	protected void checkSampleExclusiveFeature(DbExclusiveFeatureChecker<IDbKeyStructure> checker) {

		checker//
			.addErrorIf(this::isIndexKey, "Surplus index in %s.", getSampleName())
			.addErrorIf(this::isUniqueKey, "Surplus unique key in %s.", getSampleName())
			.addErrorIf(this::isPrimaryKey, "Surplus primary key in %s.", getSampleName());
	}
}
