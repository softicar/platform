package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.tuple.Tuple8;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.expressions.ISqlExpression;

abstract class SqlSelectBase8<V0, V1, V2, V3, V4, V5, V6, V7> extends AbstractSqlSelect<Tuple8<V0, V1, V2, V3, V4, V5, V6, V7>> {

	protected final SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> other;
	protected final ISqlExpression<V7> expression;
	protected final int columnIndex;

	protected SqlSelectBase8(SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> other, ISqlExpression<V7> expression) {

		this.other = other;
		this.expression = expression;
		this.columnIndex = other.columnIndex + other.expression.getColumnCount();
	}

	@Override
	public Tuple8<V0, V1, V2, V3, V4, V5, V6, V7> getResultRow(DbResultSet resultSet) {

		V7 value = expression.getValueType().getValue(resultSet, columnIndex);
		return new Tuple8<>(other.getResultRow(resultSet), value);
	}
}

