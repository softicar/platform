package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.tuple.Tuple5;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.expressions.ISqlExpression;

abstract class SqlSelectBase5<V0, V1, V2, V3, V4> extends AbstractSqlSelect<Tuple5<V0, V1, V2, V3, V4>> {

	protected final SqlSelectBase4<V0, V1, V2, V3> other;
	protected final ISqlExpression<V4> expression;
	protected final int columnIndex;

	protected SqlSelectBase5(SqlSelectBase4<V0, V1, V2, V3> other, ISqlExpression<V4> expression) {

		this.other = other;
		this.expression = expression;
		this.columnIndex = other.columnIndex + other.expression.getColumnCount();
	}

	@Override
	public Tuple5<V0, V1, V2, V3, V4> getResultRow(DbResultSet resultSet) {

		V4 value = expression.getValueType().getValue(resultSet, columnIndex);
		return new Tuple5<>(other.getResultRow(resultSet), value);
	}
}

