package com.softicar.platform.db.runtime.table.row;

/**
 * Initializer interface for {@link IDbTableRow}.
 * <p>
 * This is an internal interface that should not be used by normal code.
 *
 * @author Oliver Richers
 */
public interface IDbTableRowInitializer<R extends IDbTableRow<R, P>, P> {

	/**
	 * Disables all flags and initializes all fields to <i>null</i>.
	 *
	 * @return the initialized object (never null)
	 */
	R initializeFlagsAndSetFieldsToNull();

	/**
	 * Enables the given flags and initializes all fields to their default
	 * value.
	 *
	 * @param flags
	 *            all flags to enable
	 * @return the initialized object (never null)
	 */
	R initializeFlagsAndSetAllFieldsToDefault(DbTableRowFlag...flags);

	/**
	 * Enables the given flags and initializes the primary key fields.
	 * <p>
	 * All data fields will be initialized to <i>null</i>.
	 *
	 * @param primaryKey
	 *            the value to assign to the primary key fields (never null)
	 * @param flags
	 *            all flags to enable
	 * @return the initialized object (never null)
	 */
	R initializeFlagsAndSetPkFields(P primaryKey, DbTableRowFlag...flags);
}
