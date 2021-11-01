package com.softicar.platform.db.runtime.table.row.getter;

import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.logic.DbTableRowLoader;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Map;
import java.util.Set;

/**
 * Default implementation of {@link IDbTableRowGetterStrategy}.
 * <p>
 * This strategy loads exactly those table rows that are requested.
 *
 * @author Oliver Richers
 */
public class DbTableRowGetterStrategy<R extends IDbTableRow<R, P>, P> implements IDbTableRowGetterStrategy<R, P> {

	private final IDbTable<R, P> table;

	public DbTableRowGetterStrategy(IDbTable<R, P> table) {

		this.table = table;
	}

	@Override
	public Map<P, R> loadRowsByPrimaryKey(Set<P> keys) {

		return new DbTableRowLoader<>(table)//
			.addKeys(keys)
			.loadAsMap();
	}
}
