package com.softicar.platform.db.structure.comparison.table;

import com.softicar.platform.db.structure.table.IDbTableStructure;

public interface IDbTableStructurePair {

	IDbTableStructure getReferenceStructure();

	IDbTableStructure getSampleStructure();
}
