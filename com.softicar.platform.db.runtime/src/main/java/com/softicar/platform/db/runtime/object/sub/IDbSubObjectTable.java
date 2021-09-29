package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.entity.IDbEntityTable;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.table.IDbTable;

/**
 * Represents an {@link IDbTable} with a foreign primary key column.
 *
 * @param <R>
 *            the type of table row
 * @param <B>
 *            the type of the base table row
 * @author Oliver Richers
 */
public interface IDbSubObjectTable<R extends IDbSubObject<R, B>, B extends IDbEntity<B, P>, P> extends IDbEntityTable<R, B> {

	/**
	 * Creates a new initialized instance of {@link IDbSubObject}.
	 * <p>
	 * The base object of the new table row will be a new table row, too.
	 *
	 * @return new database object (never null)
	 */
	@Override
	R createObject();

	/**
	 * Creates a new initialized instance of {@link IDbSubObject} with the given
	 * base object.
	 *
	 * @param base
	 *            the base object to use (never null)
	 * @return a new database object (never null)
	 */
	R createObject(B base);

	/**
	 * Returns the base table of this table.
	 *
	 * @return the base table (never null)
	 */
	IDbEntityTable<B, P> getBaseTable();

	/**
	 * Returns the primary key field of this {@link IDbSubObjectTable}.
	 *
	 * @return the primary key field (never null)
	 */
	@Override
	IDbForeignRowField<R, B, P> getPrimaryKeyField();
}
