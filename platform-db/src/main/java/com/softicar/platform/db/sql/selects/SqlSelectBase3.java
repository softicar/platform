package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.tuple.Tuple3;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.expressions.ISqlExpression;

abstract class SqlSelectBase3<V0, V1, V2> extends AbstractSqlSelect<Tuple3<V0, V1, V2>> {

	protected final SqlSelectBase2<V0, V1> other;
	protected final ISqlExpression<V2> expression;
	protected final int columnIndex;

	protected SqlSelectBase3(SqlSelectBase2<V0, V1> other, ISqlExpression<V2> expression) {

		this.other = other;
		this.expression = expression;
		this.columnIndex = other.columnIndex + other.expression.getColumnCount();
	}

	@Override
	public Tuple3<V0, V1, V2> getResultRow(DbResultSet resultSet) {

		V2 value = expression.getValueType().getValue(resultSet, columnIndex);
		return new Tuple3<>(other.getResultRow(resultSet), value);
	}
}

