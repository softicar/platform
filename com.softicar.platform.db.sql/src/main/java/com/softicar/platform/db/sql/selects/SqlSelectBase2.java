package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.expressions.ISqlExpression;

abstract class SqlSelectBase2<V0, V1> extends AbstractSqlSelect<Tuple2<V0, V1>> {

	protected final SqlSelectBase1<V0> other;
	protected final ISqlExpression<V1> expression;
	protected final int columnIndex;

	protected SqlSelectBase2(SqlSelectBase1<V0> other, ISqlExpression<V1> expression) {

		this.other = other;
		this.expression = expression;
		this.columnIndex = other.columnIndex + other.expression.getColumnCount();
	}

	@Override
	public Tuple2<V0, V1> getResultRow(DbResultSet resultSet) {

		V1 value = expression.getValueType().getValue(resultSet, columnIndex);
		return new Tuple2<>(other.getResultRow(resultSet), value);
	}
}

