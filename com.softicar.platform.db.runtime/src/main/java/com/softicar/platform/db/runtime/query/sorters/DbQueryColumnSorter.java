package com.softicar.platform.db.runtime.query.sorters;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.select.IDbSqlBuilder;
import com.softicar.platform.db.sql.token.SqlKeyword;

class DbQueryColumnSorter implements IDbQuerySorter {

	private final IDbQueryColumn<?, ?> column;
	private final OrderDirection direction;

	public DbQueryColumnSorter(IDbQueryColumn<?, ?> column, OrderDirection direction) {

		this.column = column;
		this.direction = direction;
	}

	@Override
	public void buildExpression(IDbSqlBuilder builder) {

		builder.addIdentifier(column.getName());
		if (direction == OrderDirection.DESCENDING) {
			builder.addToken(SqlKeyword.DESC);
		}
	}
}
