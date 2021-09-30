package com.softicar.platform.db.structure.table;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.core.table.DbTableName;

public class DbMissingTableColumnException extends SofticarException {

	public DbMissingTableColumnException(DbTableName tableName, String columnName) {

		super("Failed to find column '%s' in table '%s'.", columnName, tableName.getCanonicalName());
	}
}
