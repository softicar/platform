package com.softicar.platform.emf.sub.entity.table;

import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.table.IEmfTable;

/**
 * Interface for {@link IEmfTable} for sub-entities.
 *
 * @param <E>
 *            the type of the {@link IEmfEntity}
 * @param <P>
 *            the type of the primary key of the {@link IEmfTable}
 * @param <B>
 *            the type of the base {@link IEmfEntity}
 * @param <Q>
 *            the type of the primary key of the base {@link IEmfTable}
 * @param <S>
 *            the type of the scope
 * @author Oliver Richers
 */
public interface IEmfSubEntityTable<E extends IEmfEntity<E, P>, P, B extends IEmfEntity<B, Q>, Q, S> extends IEmfEntityTable<E, P, S> {

	/**
	 * Returns the field of the table referencing the base {@link IEmfEntity}.
	 *
	 * @return the base field (never null)
	 */
	IDbForeignRowField<E, B, ?> getBaseField();

	/**
	 * Returns the base table.
	 *
	 * @return base table (never null)
	 */
	IEmfTable<B, Q, S> getBaseTable();

	/**
	 * Determines if the given attribute refers to the base {@link IEmfEntity}.
	 *
	 * @param attribute
	 *            the attribute to test (never null)
	 * @return whether the given attribute refers to the base {@link IEmfEntity}
	 */
	default boolean isBaseAttribute(IEmfAttribute<E, ?> attribute) {

		return getAttribute(getBaseField()) == attribute;
	}
}
