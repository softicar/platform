package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.table.row.IDbTableRowInitializer;

/**
 * Special {@link IDbTableRowInitializer} for {@link IDbSubObject}.
 * <p>
 * This is an internal interface that should not be used by normal code.
 *
 * @author Oliver Richers
 */
public interface IDbSubObjectInitializer<R extends IDbSubObject<R, B>, B extends IDbEntity<B, ?>> extends IDbTableRowInitializer<R, B> {

	/**
	 * Initializes the {@link IDbSubObject} by copying the data from the given
	 * source object.
	 * <p>
	 * The primary key field will be initialized with a copy of the base object
	 * of the source object.
	 * <p>
	 * Only the {@link DbTableRowFlag#IMPERMANENT} will be enabled.
	 *
	 * @param source
	 *            the source object to copy from (never null)
	 * @return the initialized {@link IDbSubObject} (never null)
	 */
	R initializeCopy(R source);

	/**
	 * Initializes the {@link IDbSubObject} with the given base object.
	 * <p>
	 * All data fields will be initialized to default values.
	 * <p>
	 * Only the {@link DbTableRowFlag#IMPERMANENT} will be enabled.
	 *
	 * @param base
	 *            the base object to use (never null)
	 * @return the initialized {@link IDbSubObject} (never null)
	 */
	R initializeWithBase(B base);
}
