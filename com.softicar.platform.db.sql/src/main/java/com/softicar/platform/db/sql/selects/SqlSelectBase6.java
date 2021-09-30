package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.tuple.Tuple6;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.expressions.ISqlExpression;

abstract class SqlSelectBase6<V0, V1, V2, V3, V4, V5> extends AbstractSqlSelect<Tuple6<V0, V1, V2, V3, V4, V5>> {

	protected final SqlSelectBase5<V0, V1, V2, V3, V4> other;
	protected final ISqlExpression<V5> expression;
	protected final int columnIndex;

	protected SqlSelectBase6(SqlSelectBase5<V0, V1, V2, V3, V4> other, ISqlExpression<V5> expression) {

		this.other = other;
		this.expression = expression;
		this.columnIndex = other.columnIndex + other.expression.getColumnCount();
	}

	@Override
	public Tuple6<V0, V1, V2, V3, V4, V5> getResultRow(DbResultSet resultSet) {

		V5 value = expression.getValueType().getValue(resultSet, columnIndex);
		return new Tuple6<>(other.getResultRow(resultSet), value);
	}
}

