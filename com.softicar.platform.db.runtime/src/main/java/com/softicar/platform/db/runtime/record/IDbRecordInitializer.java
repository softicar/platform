package com.softicar.platform.db.runtime.record;

import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.table.row.IDbTableRowInitializer;

/**
 * Initializer interface for {@link IDbRecord}.
 * <p>
 * This is an internal interface that should not be used by normal code.
 *
 * @author Oliver Richers
 */
public interface IDbRecordInitializer<R extends IDbRecord<R, P>, P> extends IDbTableRowInitializer<R, P> {

	/**
	 * Initializes all data fields to default.
	 * <p>
	 * The primary key fields are initialized from the given primary key.
	 * <p>
	 * The flags are initialized to {@link DbTableRowFlag#IMPERMANENT}.
	 *
	 * @param primaryKey
	 *            the primary key value (never null)
	 * @return the initialized {@link IDbRecord}
	 */
	R initializeToDefaults(P primaryKey);

	/**
	 * Creates a full copy of the given {@link IDbRecord}.
	 * <p>
	 * The values of all fields (i.e. primary key fields and data fields) are
	 * copied.
	 * <p>
	 * The flags are initialized as specified.
	 *
	 * @param record
	 *            the {@link IDbRecord} to copy from (never null)
	 * @param flagsToEnable
	 *            the {@link DbTableRowFlag}s to enable
	 * @return the initialized {@link IDbRecord}
	 */
	R initializeFullCopy(R record, DbTableRowFlag...flagsToEnable);
}
