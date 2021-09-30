package com.softicar.platform.db.structure.table;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowStructure;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import com.softicar.platform.db.structure.key.IDbKeyStructure;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Describes the structure of a database table.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public interface IDbTableStructure {

	/**
	 * Returns the {@link DbTableName} of the database table.
	 *
	 * @return the {@link DbTableName} (never null)
	 */
	DbTableName getTableName();

	/**
	 * Returns the {@link IDbColumnStructure} that represents the ID column of
	 * the table.
	 * <p>
	 * If the tables does not have an ID column, null is returned.
	 *
	 * @return the {@link IDbColumnStructure} of the ID column
	 */
	Optional<IDbColumnStructure> getIdColumn();

	/**
	 * Returns the {@link IDbColumnStructure} that represents the primary key
	 * (PK) column of the table.
	 * <p>
	 * If the tables does not have a PK column, null is returned.
	 *
	 * @return the {@link IDbColumnStructure} of the PK column
	 */
	Optional<IDbColumnStructure> getPkColumn();

	/**
	 * Returns the {@link IDbColumnStructure} of the table column with the given
	 * physical name.
	 *
	 * @param columnName
	 *            the physical column name (never null)
	 * @return the column structure
	 */
	Optional<IDbColumnStructure> getColumnByPhysicalName(String columnName);

	/**
	 * Returns the {@link IDbColumnStructure} of the table column with the given
	 * physical name.
	 *
	 * @param columnName
	 *            the physical column name (never null)
	 * @return the column structure (never null)
	 * @throws DbMissingTableColumnException
	 *             if no column with that name could be found
	 */
	IDbColumnStructure getColumnByPhysicalNameOrThrow(String columnName);

	/**
	 * Returns the structures of all columns of this table.
	 * <p>
	 * Returns the table columns in the order, in which they are defined in the
	 * table.
	 *
	 * @return the structures of all table columns (never null)
	 */
	List<IDbColumnStructure> getColumns();

	/**
	 * Returns the {@link IDbKeyStructure} of the primary key of this table.
	 *
	 * @return the structure of the primary table key
	 */
	Optional<IDbKeyStructure> getPrimaryKey();

	/**
	 * Returns the structures of all keys of this table.
	 * <p>
	 * The returned {@link Collection} contains all unique and index keys, as
	 * well as the primary key.
	 *
	 * @return all keys of this table (never null)
	 */
	List<IDbKeyStructure> getKeys();

	/**
	 * Returns the structures of all foreign keys of this table.
	 *
	 * @return all foreign keys of this table (never null)
	 */
	List<IDbForeignKeyStructure> getForeignKeys();

	/**
	 * Returns a list of the {@link IDbEnumTableRowStructure} instances that
	 * belong to this {@link IDbTableStructure}.
	 * <p>
	 * For each enum table row, the returned list contains an
	 * {@link IDbEnumTableRowStructure}.
	 * <p>
	 * Returns an empty list if this {@link IDbTableStructure} does not
	 * correspond to an enum table, that is, if {@link #isEnumTable()} returns
	 * <i>false</i>.
	 *
	 * @return a list of {@link IDbEnumTableRowStructure} instances (never null)
	 */
	List<IDbEnumTableRowStructure> getEnumTableRows();

	/**
	 * Returns whether this is an enum table or not.
	 *
	 * @return <i>true</i> if this is an enum table; <i>false</i> otherwise
	 */
	boolean isEnumTable();

	/**
	 * Returns the comment on this table.
	 * <p>
	 * By default, an empty string is returned.
	 *
	 * @return the table comment (never null)
	 */
	String getComment();
}
