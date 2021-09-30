package com.softicar.platform.db.sql.statement;

import java.util.function.Consumer;

/**
 * This class represents a <i>SELECT</i> query over a database table.
 *
 * @param <R>
 *            the row type of the selected table
 * @author Oliver Richers
 */
public interface ISqlSelect<R> extends ISqlSelectOrJoin<R, R, ISqlSelect<R>> {

	/**
	 * Executes the given customizer on this object.
	 * <p>
	 * This method facilitates the customization of an {@link ISqlSelect} when
	 * chaining methods, that is, no local variable needs to be declared just
	 * for customization.
	 *
	 * <pre>
	 * {@code
	 * return createSelect()//
	 *     .join(...)
	 *     .where(...)
	 *     .customize(this::addMoreConditions)
	 *     .list();
	 * }
	 * </pre>
	 *
	 * @param customizer
	 *            the customizer to apply (never null)
	 * @return this object
	 */
	default ISqlSelect<R> customize(Consumer<ISqlSelect<R>> customizer) {

		customizer.accept(this);
		return this;
	}
}
