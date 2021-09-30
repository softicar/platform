package com.softicar.platform.db.structure.foreign.key;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.table.IDbTableStructureFeature;
import java.util.List;
import java.util.Map;

/**
 * Describes the structure of a foreign key of a database table.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public interface IDbForeignKeyStructure extends IDbTableStructureFeature {

	/**
	 * Returns a {@link List} of all {@link DbForeignKeyColumnPair} elements.
	 * <p>
	 * The order of the pairs in this list reflects the order in which the
	 * columns physically occur in the foreign key.
	 *
	 * @return all columns names (never null)
	 */
	List<DbForeignKeyColumnPair> getColumnPairs();

	/**
	 * Returns a {@link List} of the physical names of the source columns
	 * covered by this foreign key.
	 * <p>
	 * The order of the columns in this list reflects the order in which the
	 * columns physically occur in the foreign key. This order is important when
	 * matching those columns to the columns in the target table.
	 *
	 * @return all columns names (never null)
	 */
	List<String> getColumns();

	/**
	 * Returns a {@link List} of the physical names of the target columns
	 * covered by this foreign key.
	 * <p>
	 * The order of the columns in this list reflects the order in which the
	 * columns physically occur in the foreign key.
	 *
	 * @return all foreign columns names (never null)
	 */
	List<String> getForeignColumns();

	/**
	 * Returns the name of the foreign database table.
	 *
	 * @return the name of the foreign database table (never null)
	 */
	DbTableName getForeignTableName();

	/**
	 * Returns the physical name of the foreign column to which the column with
	 * the given physical name points.
	 *
	 * @param column
	 *            the physical column name (never null)
	 * @return the physical name of the foreign column (may be null)
	 */
	String getForeignColumnName(String column);

	/**
	 * Returns a {@link Map} that associates physical column names of the source
	 * table with referenced physical column names of the target table.
	 *
	 * @return a map of physical column names, with source table columns as
	 *         keys, and foreign table columns as values (never null)
	 */
	Map<String, String> getColumnNameMap();

	/**
	 * Returns the {@link DbForeignKeyAction} to perform upon deletion of the
	 * referenced record.
	 *
	 * @return the {@link DbForeignKeyAction} to perform upon deletion of the
	 *         referenced record (never null)
	 */
	DbForeignKeyAction getOnDeleteAction();

	/**
	 * Returns the {@link DbForeignKeyAction} to perform upon update of the
	 * referenced value.
	 *
	 * @return the {@link DbForeignKeyAction} to perform upon update of the
	 *         referenced value (never null)
	 */
	DbForeignKeyAction getOnUpdateAction();
}
