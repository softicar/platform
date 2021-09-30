package com.softicar.platform.db.runtime.query.sorters;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.select.IDbSqlBuilder;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;

class DbQueryEnumColumnSorter implements IDbQuerySorter {

	private final IDbQueryColumn<?, ?> column;
	private final OrderDirection direction;

	public DbQueryEnumColumnSorter(IDbQueryColumn<?, ?> column, OrderDirection direction) {

		this.column = column;
		this.direction = direction;
	}

	@Override
	public void buildExpression(IDbSqlBuilder builder) {

		builder.addToken(SqlKeyword.CAST);
		builder.addToken(SqlSymbol.LEFT_PARENTHESIS);
		builder.addIdentifier(column.getName());
		builder.addToken(SqlKeyword.AS);
		builder.addToken(SqlKeyword.CHAR);
		builder.addToken(SqlSymbol.RIGHT_PARENTHESIS);

		if (direction == OrderDirection.DESCENDING) {
			builder.addToken(SqlKeyword.DESC);
		}
	}
}
