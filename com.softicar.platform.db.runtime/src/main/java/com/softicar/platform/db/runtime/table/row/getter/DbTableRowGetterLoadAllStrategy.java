package com.softicar.platform.db.runtime.table.row.getter;

import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This strategy always loads all table rows.
 * <p>
 * For tables with very few table rows, e.g. static enumeration tables, this
 * strategy is the most efficient.
 *
 * @author Oliver Richers
 */
public class DbTableRowGetterLoadAllStrategy<R extends IDbTableRow<R, P>, P> implements IDbTableRowGetterStrategy<R, P> {

	private final IDbTable<R, P> table;

	public DbTableRowGetterLoadAllStrategy(IDbTable<R, P> table) {

		this.table = table;
	}

	@Override
	public Map<P, R> loadRowsByPrimaryKey(Set<P> keys) {

		Map<P, R> result = new TreeMap<>();
		keys.forEach(key -> result.put(key, null));
		for (R row: table.createSelect()) {
			P key = row.pk();
			if (result.containsKey(key)) {
				result.put(key, row);
			}
		}
		return result;
	}
}
