package com.softicar.platform.db.runtime.structure.comparison.table;

import com.softicar.platform.db.structure.table.IDbTableStructure;

public interface IDbTableStructurePair {

	IDbTableStructure getReferenceStructure();

	IDbTableStructure getSampleStructure();
}
