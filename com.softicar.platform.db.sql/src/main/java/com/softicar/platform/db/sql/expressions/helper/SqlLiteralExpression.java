package com.softicar.platform.db.sql.expressions.helper;

import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

public final class SqlLiteralExpression<VALUE> implements ISqlExpression0<VALUE> {

	private final ISqlValueType<VALUE> valueType;
	private final VALUE value;

	public SqlLiteralExpression(ISqlValueType<VALUE> valueType, VALUE value) {

		this.valueType = valueType;
		this.value = value;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		builder.addParameter(value);
	}

	@Override
	public ISqlValueType<VALUE> getValueType() {

		return valueType;
	}

	@Override
	public int getColumnCount() {

		return 1;
	}

	public static <V> SqlLiteralExpression<V> create(ISqlValueType<V> valueType, V value) {

		return new SqlLiteralExpression<>(valueType, value);
	}
}
