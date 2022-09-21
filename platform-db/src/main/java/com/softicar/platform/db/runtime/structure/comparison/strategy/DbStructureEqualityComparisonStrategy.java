package com.softicar.platform.db.runtime.structure.comparison.strategy;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.structure.comparison.column.DbColumnStructureEqualityComparer;
import com.softicar.platform.db.runtime.structure.comparison.enumeration.DbEnumTableRowStructureEqualityComparer;
import com.softicar.platform.db.runtime.structure.comparison.feature.IDbFeatureStructureComparer;
import com.softicar.platform.db.runtime.structure.comparison.foreign.key.DbForeignKeyStructureEqualityComparer;
import com.softicar.platform.db.runtime.structure.comparison.key.DbKeyStructureEqualityComparer;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;

public class DbStructureEqualityComparisonStrategy implements IDbStructureComparisonStrategy {

	private final IDisplayString referenceName;
	private final IDisplayString sampleName;

	public DbStructureEqualityComparisonStrategy() {

		this(IDisplayString.create("Reference"), IDisplayString.create("Sample"));
	}

	public DbStructureEqualityComparisonStrategy(IDisplayString referenceName, IDisplayString sampleName) {

		this.referenceName = referenceName;
		this.sampleName = sampleName;
	}

	@Override
	public IDbFeatureStructureComparer getColumnComparer(IDbTableStructureComparerConfiguration configuration) {

		return new DbColumnStructureEqualityComparer(configuration);
	}

	@Override
	public IDbFeatureStructureComparer getForeignKeyComparer(IDbTableStructureComparerConfiguration configuration) {

		return new DbForeignKeyStructureEqualityComparer(configuration);
	}

	@Override
	public IDbFeatureStructureComparer getKeyComparer(IDbTableStructureComparerConfiguration configuration) {

		return new DbKeyStructureEqualityComparer(configuration);
	}

	@Override
	public IDbFeatureStructureComparer getEnumTableRowComparer(IDbTableStructureComparerConfiguration configuration) {

		return new DbEnumTableRowStructureEqualityComparer(configuration);
	}

	@Override
	public IDisplayString getReferenceName() {

		return referenceName;
	}

	@Override
	public IDisplayString getSampleName() {

		return sampleName;
	}
}
