package com.softicar.platform.db.structure.database;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.view.IDbViewStructure;
import java.util.Collection;
import java.util.Set;

/**
 * Describes the structure of a database.
 *
 * @author Oliver Richers
 */
public interface IDbDatabaseStructure {

	/**
	 * Returns the names of all table structures.
	 *
	 * @return set of all table names (never null)
	 */
	Set<DbTableName> getTableNames();

	/**
	 * Returns all table structures.
	 *
	 * @return collection of all table structures (never null)
	 */
	Collection<IDbTableStructure> getTableStructures();

	/**
	 * Returns the {@link IDbTableStructure} for the given table name.
	 *
	 * @param tableName
	 *            the name of the table (never null)
	 * @return the corresponding {@link IDbTableStructure} or <i>null</i> if no
	 *         such table could be found
	 */
	IDbTableStructure getTableStructure(DbTableName tableName);

	/**
	 * Returns the names of all view structures.
	 *
	 * @return set of all view names (never null)
	 */
	Set<DbTableName> getViewNames();

	/**
	 * Returns all view structures.
	 *
	 * @return collection of all view structures (never null)
	 */
	Collection<IDbViewStructure> getViewStructures();

	/**
	 * Returns the {@link IDbViewStructure} for the given view name.
	 *
	 * @param viewName
	 *            the name of the view (never null)
	 * @return the corresponding {@link IDbViewStructure} or <i>null</i> if no
	 *         such view could be found
	 */
	IDbViewStructure getViewStructure(DbTableName viewName);
}
