package com.softicar.platform.db.runtime.record;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Represents a row in an {@link IDbRecordTable}.
 *
 * @author Oliver Richers
 */
public interface IDbRecord<R extends IDbRecord<R, P>, P> extends IDbTableRow<R, P>, Comparable<R> {

	@Override
	IDbRecordTable<R, P> table();

	@Override
	IDbRecordInitializer<R, P> initializer();
}
