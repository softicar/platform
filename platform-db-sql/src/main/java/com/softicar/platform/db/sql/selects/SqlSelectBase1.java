package com.softicar.platform.db.sql.selects;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.expressions.ISqlExpression;

abstract class SqlSelectBase1<V0> extends AbstractSqlSelect<V0> {

	protected final SqlSelectBase0 other;
	protected final ISqlExpression<V0> expression;
	protected final int columnIndex;

	protected SqlSelectBase1(SqlSelectBase0 other, ISqlExpression<V0> expression) {

		this.other = other;
		this.expression = expression;
		this.columnIndex = 1;
	}

	@Override
	public V0 getResultRow(DbResultSet resultSet) {

		return expression.getValueType().getValue(resultSet, columnIndex);
	}
}

