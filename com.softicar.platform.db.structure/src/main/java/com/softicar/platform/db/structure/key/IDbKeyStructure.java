package com.softicar.platform.db.structure.key;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.table.IDbTableStructureFeature;
import java.util.List;

/**
 * Describes the structure of a database table key.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public interface IDbKeyStructure extends IDbTableStructureFeature {

	/**
	 * Returns the {@link DbKeyType} of this key.
	 *
	 * @return the {@link DbKeyType} of this key (never null)
	 */
	DbKeyType getType();

	/**
	 * Tests if the given {@link IDbColumnStructure} is contained in this key.
	 *
	 * @param column
	 *            the {@link IDbColumnStructure} that represents the column to
	 *            test (never null)
	 * @return <i>true</i> if this key contains the column; <i>false</i>
	 *         otherwise
	 */
	boolean containsColumn(IDbColumnStructure column);

	/**
	 * Returns a {@link List} of the column names that this key contains.
	 * <p>
	 * Returns the table column names in the order, in which they are defined in
	 * the key.
	 *
	 * @return the column names that this key contains (never null)
	 */
	List<String> getColumnNames();

	/**
	 * Returns a {@link List} of the columns that this key contains.
	 * <p>
	 * Returns the table columns in the order, in which they are defined in the
	 * key.
	 *
	 * @return the columns that this key contains (never null)
	 */
	List<IDbColumnStructure> getColumns();
}
