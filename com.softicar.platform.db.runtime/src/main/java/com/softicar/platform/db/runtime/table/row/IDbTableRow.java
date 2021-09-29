package com.softicar.platform.db.runtime.table.row;

import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.sql.ISqlTableRow;

public interface IDbTableRow<R extends IDbTableRow<R, P>, P> extends IDbBasicTableRow<R>, ISqlTableRow<R, P> {

	@Override
	IDbTable<R, P> table();

	/**
	 * Returns an {@link IDbTableRowInitializer} for this {@link IDbTableRow}.
	 * <p>
	 * This is an internal method that should not be used by normal code.
	 *
	 * @return the initializer (never null)
	 */
	IDbTableRowInitializer<R, P> initializer();
}
