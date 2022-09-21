package com.softicar.platform.db.runtime.structure.comparison.table;

import com.softicar.platform.db.structure.table.IDbTableStructure;

public class DbTableStructurePair implements IDbTableStructurePair {

	private final IDbTableStructure referenceStructure;
	private final IDbTableStructure sampleStructure;

	public DbTableStructurePair(IDbTableStructure referenceStructure, IDbTableStructure sampleStructure) {

		this.referenceStructure = referenceStructure;
		this.sampleStructure = sampleStructure;
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
