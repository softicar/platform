package com.softicar.platform.db.runtime.structure.comparison.enumeration;

import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.db.runtime.structure.comparison.feature.checker.DbExclusiveFeatureChecker;
import com.softicar.platform.db.runtime.structure.comparison.feature.checker.DbMutualFeatureChecker;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.enums.DbEnumTableRowStructureToStringConverter;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowStructure;
import java.util.function.Function;

public class DbEnumTableRowStructureEqualityComparer extends AbstractDbEnumTableRowStructureComparer {

	public DbEnumTableRowStructureEqualityComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration);
	}

	@Override
	protected void checkMutualFeature(DbMutualFeatureChecker<IDbEnumTableRowStructure> checker) {

		checker//
			.compare("Values", Function.identity(), new DbEnumTableRowStructureToStringConverter()::convert)
			.addErrorIf(new DbEnumTableRowStructureComparer()::isNotEqual);
	}

	@Override
	protected void checkReferenceExclusiveFeature(DbExclusiveFeatureChecker<IDbEnumTableRowStructure> checker) {

		checker//
			.addErrorIf(Predicates.always(), "Surplus enum table row in %s.", getReferenceName());
	}

	@Override
	protected void checkSampleExclusiveFeature(DbExclusiveFeatureChecker<IDbEnumTableRowStructure> checker) {

		checker//
			.addErrorIf(Predicates.always(), "Surplus enum table row in %s.", getSampleName());
	}
}
