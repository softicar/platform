package com.softicar.platform.db.structure.enums;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.table.IDbTableStructureFeature;
import java.util.Collection;

/**
 * Describes the structure of a single enum constant, defined as a table row in
 * an enum database table.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IDbEnumTableRowStructure extends IDbTableStructureFeature {

	/**
	 * Returns the ID of the enum table row.
	 *
	 * @return the ID of the enum table row (may be null)
	 */
	Integer getId();

	/**
	 * Returns the value for the given {@link IDbColumnStructure}.
	 *
	 * @param columnStructure
	 *            the column structure (never null)
	 * @return the corresponding value (never null)
	 */
	IDbEnumTableRowValue getValue(IDbColumnStructure columnStructure);

	/**
	 * Returns the columns for which this {@link IDbEnumTableRowStructure}
	 * defines values.
	 *
	 * @return the columns for which this {@link IDbEnumTableRowStructure}
	 *         defines values (never null)
	 */
	Collection<IDbColumnStructure> getDefinedColumns();
}
