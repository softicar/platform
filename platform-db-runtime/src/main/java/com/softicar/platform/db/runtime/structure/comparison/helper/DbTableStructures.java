package com.softicar.platform.db.runtime.structure.comparison.helper;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.table.IDbTableStructure;

public interface DbTableStructures {

	static DbTableName getUniqueTableNameOrThrow(IDbTableStructure first, IDbTableStructure second) {

		return DbStructures
			.getUniquePropertyValueOrThrow(//
				first,
				second,
				IDbTableStructure::getTableName,
				"Full Table Name");
	}
}
