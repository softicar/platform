package com.softicar.platform.db.runtime.structure.comparison.table;

import com.softicar.platform.db.runtime.structure.comparison.strategy.IDbStructureComparisonStrategy;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Objects;

public class DbTableStructureComparerConfiguration implements IDbTableStructureComparerConfiguration {

	private final IDbStructureComparisonStrategy strategy;
	private final IDbTableStructure referenceStructure;
	private final IDbTableStructure sampleStructure;

	public DbTableStructureComparerConfiguration(IDbStructureComparisonStrategy strategy, IDbTableStructure referenceTableStructure,
			IDbTableStructure sampleTableStructure) {

		this(strategy, new DbTableStructurePair(referenceTableStructure, sampleTableStructure));
	}

	public DbTableStructureComparerConfiguration(IDbStructureComparisonStrategy strategy, IDbTableStructurePair tableStructurePair) {

		Objects.requireNonNull(tableStructurePair);
		this.strategy = Objects.requireNonNull(strategy);
		this.referenceStructure = Objects.requireNonNull(tableStructurePair.getReferenceStructure());
		this.sampleStructure = Objects.requireNonNull(tableStructurePair.getSampleStructure());
	}

	@Override
	public IDbStructureComparisonStrategy getStrategy() {

		return strategy;
	}

	@Override
	public IDbTableStructure getReferenceStructure() {

		return referenceStructure;
	}

	@Override
	public IDbTableStructure getSampleStructure() {

		return sampleStructure;
	}
}
