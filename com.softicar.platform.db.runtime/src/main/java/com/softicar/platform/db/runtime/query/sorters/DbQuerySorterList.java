package com.softicar.platform.db.runtime.query.sorters;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableSorterList;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.query.builder.IDbQuerySqlBuilder;
import java.util.ArrayList;
import java.util.List;

public class DbQuerySorterList<R> implements IDataTableSorterList<R> {

	private final List<IDbQuerySorter> sorters = new ArrayList<>();

	@Override
	public void clear() {

		sorters.clear();
	}

	@Override
	public boolean isEmpty() {

		return sorters.isEmpty();
	}

	@Override
	public void addSorter(IDataTableColumn<R, ?> column, OrderDirection orderDirection) {

		IDbQueryColumn<?, ?> queryColumn = (IDbQueryColumn<?, ?>) column;
		this.sorters.add(DbQuerySorters.createSorter(queryColumn, orderDirection));
	}

	public void applyTo(IDbQuerySqlBuilder builder) {

		builder.setSorters(sorters);
	}
}
