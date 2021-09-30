package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.runtime.entity.IDbEntity;

/**
 * Represents a row in an {@link IDbSubObjectTable}.
 *
 * @param <R>
 *            the type of the table row
 * @param <B>
 *            the type of the base table row
 * @author Oliver Richers
 */
public interface IDbSubObject<R extends IDbSubObject<R, B>, B extends IDbEntity<B, ?>> extends IDbEntity<R, B> {

	/**
	 * Returns an {@link IDbSubObjectTable} object representing the structure of
	 * the database table.
	 *
	 * @return the associated {@link IDbSubObjectTable} object
	 */
	@Override
	IDbSubObjectTable<R, B, ?> table();

	/**
	 * Returns the ID of the base object.
	 * <p>
	 * If the base object has not be saved yet, this will return <i>null</i>.
	 *
	 * @return the ID of the base object (may be null)
	 */
	@Override
	Integer getId();

	/**
	 * Compares this objects to the given {@link IBasicItem}.
	 * <p>
	 * Objects will be compared by ID first. Only if they have the same ID,
	 * their data fields will be compared lexicographically with respect to the
	 * column position in the database table structure. For example, a field
	 * with column index 0 has a higher weight than a field with column index 1.
	 */
	@Override
	int compareTo(IBasicItem other);

	/**
	 * Creates a copy of this object.
	 * <p>
	 * The values of all data fields are copied.
	 * <p>
	 * This method will also create a copy of the base object.
	 *
	 * @return the new object (never null)
	 */
	@Override
	R copy();

	/**
	 * Returns an {@link IDbSubObjectInitializer} for this {@link IDbSubObject}.
	 * <p>
	 * This is an internal method that should not be used by normal code.
	 *
	 * @return the initializer (never null)
	 */
	@Override
	IDbSubObjectInitializer<R, B> initializer();
}
