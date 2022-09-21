package com.softicar.platform.db.runtime.entity;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Represents a row in an {@link IDbEntityTable}.
 *
 * @param <R>
 *            the type of the table row
 * @param <P>
 *            the type of the primary key field
 * @author Oliver Richers
 */
public interface IDbEntity<R extends IDbEntity<R, P>, P> extends IDbTableRow<R, P>, IBasicItem {

	/**
	 * Returns an {@link IDbEntityTable} that represents the structure of the
	 * database table.
	 *
	 * @return the associated {@link IDbEntityTable} (never null)
	 */
	@Override
	IDbEntityTable<R, P> table();

	/**
	 * Returns the ID of this entity.
	 * <p>
	 * Please note that this method will be removed in the long run in favor of
	 * {@link IBasicItem#getItemId()}. The reason is that this method implies
	 * that the ID is an {@link Integer} which is too restrictive. We want to
	 * support IDs of type {@link Long}.
	 *
	 * @return the ID of this entity (may be null)
	 */
	@Override
	Integer getId();

	/**
	 * Compares this entity to the given {@link IBasicItem}.
	 * <p>
	 * If the given {@link IBasicItem} is does not implement the same class as
	 * this entity, an exception will the thrown.
	 *
	 * @param other
	 *            the basic item to compare to
	 * @return the comparison result as defined by {@link Comparable#compareTo}
	 */
	@Override
	int compareTo(IBasicItem other);

	/**
	 * Creates a copy of this entity.
	 *
	 * @return the new entity (never null)
	 */
	R copy();
}
