package com.softicar.platform.db.runtime.enums;

import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.table.configuration.IDbTableDataInitializer;
import java.util.Collection;

/**
 * Database table class for enum tables (static reference data).
 * <p>
 * This table assumes that there is a corresponding {@link Enum} class,
 * enumerating all rows of the respective database table.
 * <p>
 * This class configures a {@link IDbTableDataInitializer} to insert all values
 * of the {@link Enum} class. Furthermore, it installs a strategy which
 * automatically pre-fetches all entries of the database table, as soon as one
 * entry is accessed by ID.
 *
 * @param <R>
 *            the type of the database table rows
 * @param <E>
 *            the type of the {@link Enum} class
 * @author Oliver Richers
 */
public interface IDbEnumTable<R extends IDbEnumTableRow<R, E>, E extends IDbEnumTableRowEnum<E, R>> extends IDbObjectTable<R> {

	/**
	 * Returns all enum values of the corresponding {@link IDbEnumTableRowEnum}
	 * class.
	 *
	 * @return all enum values (never null)
	 */
	Collection<E> getEnums();

	/**
	 * Returns the enum value corresponding to the given ID.
	 *
	 * @param id
	 *            the ID (never null)
	 * @return the corresponding enum value (may be null)
	 */
	E getEnumById(Integer id);
}
