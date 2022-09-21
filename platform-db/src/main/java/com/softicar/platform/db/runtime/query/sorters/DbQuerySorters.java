package com.softicar.platform.db.runtime.query.sorters;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;

public class DbQuerySorters {

	public static IDbQuerySorter createSorter(IDbQueryColumn<?, ?> column, OrderDirection direction) {

		if (column.getValueClass().isEnum()) {
			return new DbQueryEnumColumnSorter(column, direction);
		} else {
			return new DbQueryColumnSorter(column, direction);
		}
	}
}
