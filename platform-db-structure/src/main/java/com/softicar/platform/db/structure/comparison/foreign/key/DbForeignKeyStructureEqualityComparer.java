package com.softicar.platform.db.structure.comparison.foreign.key;

import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.db.structure.comparison.feature.checker.DbExclusiveFeatureChecker;
import com.softicar.platform.db.structure.comparison.feature.checker.DbMutualFeatureChecker;
import com.softicar.platform.db.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;

public class DbForeignKeyStructureEqualityComparer extends AbstractDbForeignKeyStructureComparer {

	public DbForeignKeyStructureEqualityComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration);
	}

	@Override
	protected void checkMutualFeature(DbMutualFeatureChecker<IDbForeignKeyStructure> checker) {

		checker//
			.compare("Foreign Table Name", IDbForeignKeyStructure::getForeignTableName)
			.addErrorIf(Values::notEqual)

			.compare("Column Name Map", IDbForeignKeyStructure::getColumnNameMap)
			.addErrorIf(Values::notEqual)

			.compare("On-Delete Action", IDbForeignKeyStructure::getOnDeleteAction)
			.addErrorIf(Values::notEqual)

			.compare("On-Update Action", IDbForeignKeyStructure::getOnUpdateAction)
			.addErrorIf(Values::notEqual);
	}

	@Override
	protected void checkReferenceExclusiveFeature(DbExclusiveFeatureChecker<IDbForeignKeyStructure> checker) {

		checker//
			.addErrorIf(Predicates.always(), "Surplus constraint in %s.", getReferenceName());
	}

	@Override
	protected void checkSampleExclusiveFeature(DbExclusiveFeatureChecker<IDbForeignKeyStructure> checker) {

		checker//
			.addErrorIf(Predicates.always(), "Surplus constraint in %s.", getSampleName());
	}
}
