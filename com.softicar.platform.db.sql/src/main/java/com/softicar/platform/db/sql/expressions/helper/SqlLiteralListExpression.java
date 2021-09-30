package com.softicar.platform.db.sql.expressions.helper;

import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

public final class SqlLiteralListExpression<VALUE> implements ISqlExpression0<VALUE> {

	private final ISqlValueType<VALUE> valueType;
	private final Iterable<VALUE> values;

	private SqlLiteralListExpression(ISqlValueType<VALUE> valueType, Iterable<VALUE> values) {

		this.valueType = valueType;
		this.values = values;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		builder.addParameterPack(values);
	}

	@Override
	public ISqlValueType<VALUE> getValueType() {

		return valueType;
	}

	@Override
	public int getColumnCount() {

		return 1;
	}

	public static <V> SqlLiteralListExpression<V> create(ISqlValueType<V> valueType, Iterable<V> values) {

		return new SqlLiteralListExpression<>(valueType, values);
	}
}
