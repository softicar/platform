package com.softicar.platform.db.runtime.entity;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.IDbTable;

/**
 * Represents an {@link IDbTable} that contains rows of type {@link IDbEntity}.
 *
 * @param <R>
 *            the type of table row
 * @param <P>
 *            the type of the primary key field
 * @author Oliver Richers
 */
public interface IDbEntityTable<R extends IDbEntity<R, P>, P> extends IDbTable<R, P> {

	/**
	 * Returns a stub entity for the given ID.
	 *
	 * @param id
	 *            the ID (may be null)
	 * @return the stub entity or null if ID was null
	 */
	R getStub(Integer id);

	/**
	 * Creates a new initialized instance of {@link IDbEntity}.
	 *
	 * @return new table row (never null)
	 */
	R createObject();

	/**
	 * Returns the primary key field of this table.
	 *
	 * @return the primary key field (never null)
	 */
	IDbField<R, P> getPrimaryKeyField();
}
