package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.tuple.Tuple7;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.expressions.ISqlExpression;

abstract class SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> extends AbstractSqlSelect<Tuple7<V0, V1, V2, V3, V4, V5, V6>> {

	protected final SqlSelectBase6<V0, V1, V2, V3, V4, V5> other;
	protected final ISqlExpression<V6> expression;
	protected final int columnIndex;

	protected SqlSelectBase7(SqlSelectBase6<V0, V1, V2, V3, V4, V5> other, ISqlExpression<V6> expression) {

		this.other = other;
		this.expression = expression;
		this.columnIndex = other.columnIndex + other.expression.getColumnCount();
	}

	@Override
	public Tuple7<V0, V1, V2, V3, V4, V5, V6> getResultRow(DbResultSet resultSet) {

		V6 value = expression.getValueType().getValue(resultSet, columnIndex);
		return new Tuple7<>(other.getResultRow(resultSet), value);
	}
}

