package com.softicar.platform.db.runtime.query;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;

/**
 * Factory interface to create {@link IDbQueryRow} from a {@link DbResultSet}.
 *
 * @author Oliver Richers
 */
public interface IDbQueryRowFactory<R extends IDbQueryRow<R>> {

	/**
	 * Creates a new {@link IDbQueryRow} from the current values of the given
	 * {@link DbResultSet}.
	 *
	 * @param select
	 *            the executed select statement
	 * @param resultSet
	 *            the result set to load from
	 * @return a new row (never null)
	 */
	R createRow(IDbSqlSelect select, DbResultSet resultSet);
}
