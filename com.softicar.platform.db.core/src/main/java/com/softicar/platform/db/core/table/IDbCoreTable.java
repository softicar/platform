package com.softicar.platform.db.core.table;

/**
 * Core interface of all database tables.
 *
 * @author Oliver Richers
 */
public interface IDbCoreTable {

	/**
	 * Returns the fully qualified name of this table.
	 *
	 * @return the fully qualified table name, never null
	 */
	DbTableName getFullName();
}
