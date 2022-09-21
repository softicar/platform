package com.softicar.platform.db.runtime.logic;

import com.softicar.platform.db.runtime.entity.IDbEntity;

/**
 * Represents a row in an {@link IDbObjectTable}.
 *
 * @param <R>
 *            the type of the table row
 * @author Oliver Richers
 */
public interface IDbObject<R extends IDbObject<R>> extends IDbEntity<R, Integer> {

	/**
	 * Returns an {@link IDbObjectTable} object representing the structure of
	 * the database table.
	 *
	 * @return the associated {@link IDbObjectTable} object
	 */
	@Override
	IDbObjectTable<R> table();

	/**
	 * Returns the automatically generated ID of this object.
	 * <p>
	 * If the object has not be saved yet, this will return <i>null</i>.
	 *
	 * @return the ID of this object (may be null)
	 */
	@Override
	Integer getId();

	/**
	 * Creates a copy of this object.
	 * <p>
	 * The values of all data fields are copied.
	 * <p>
	 * The ID field will be initialized with <i>null</i>.
	 *
	 * @return the new object (never null)
	 */
	@Override
	R copy();
}
