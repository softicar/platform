package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.tuple.Tuple4;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.expressions.ISqlExpression;

abstract class SqlSelectBase4<V0, V1, V2, V3> extends AbstractSqlSelect<Tuple4<V0, V1, V2, V3>> {

	protected final SqlSelectBase3<V0, V1, V2> other;
	protected final ISqlExpression<V3> expression;
	protected final int columnIndex;

	protected SqlSelectBase4(SqlSelectBase3<V0, V1, V2> other, ISqlExpression<V3> expression) {

		this.other = other;
		this.expression = expression;
		this.columnIndex = other.columnIndex + other.expression.getColumnCount();
	}

	@Override
	public Tuple4<V0, V1, V2, V3> getResultRow(DbResultSet resultSet) {

		V3 value = expression.getValueType().getValue(resultSet, columnIndex);
		return new Tuple4<>(other.getResultRow(resultSet), value);
	}
}

