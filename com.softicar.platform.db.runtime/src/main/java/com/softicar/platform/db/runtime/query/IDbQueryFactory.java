package com.softicar.platform.db.runtime.query;

import java.util.List;

/**
 * Factory interface to create instances of {@link IDbQuery}.
 * 
 * @author Oliver Richers
 */
public interface IDbQueryFactory<R extends IDbQueryRow<R>> {

	/**
	 * Returns a list of all columns of the query.
	 * 
	 * @return list of all query columns (never null)
	 */
	List<IDbQueryColumn<R, ?>> getColumns();
}
