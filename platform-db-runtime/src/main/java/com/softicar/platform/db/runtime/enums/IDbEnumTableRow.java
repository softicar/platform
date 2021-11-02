package com.softicar.platform.db.runtime.enums;

import com.softicar.platform.db.runtime.logic.IDbObject;

/**
 * Represents a table row of an {@link IDbEnumTable}.
 *
 * @author Oliver Richers
 */
public interface IDbEnumTableRow<R extends IDbEnumTableRow<R, E>, E extends IDbEnumTableRowEnum<E, R>> extends IDbObject<R> {

	/**
	 * Returns the {@link IDbEnumTable} which corresponds to the structure of
	 * the database table.
	 *
	 * @return the associated {@link IDbEnumTable} object
	 */
	@Override
	IDbEnumTable<R, E> table();

	/**
	 * Returns the enum value corresponding to this table row.
	 *
	 * @return the enum value (may be null)
	 */
	E getEnum();
}
