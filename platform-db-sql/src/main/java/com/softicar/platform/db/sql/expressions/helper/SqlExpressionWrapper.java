package com.softicar.platform.db.sql.expressions.helper;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

public class SqlExpressionWrapper<V> implements ISqlExpression<V> {

	private final ISqlExpression<V> expression;

	protected SqlExpressionWrapper(ISqlExpression<V> expression) {

		this.expression = expression;
	}

	@Override
	public final void build(ISqlClauseBuilder builder) {

		expression.build(builder);
	}

	@Override
	public final ISqlValueType<V> getValueType() {

		return expression.getValueType();
	}

	@Override
	public final int getColumnCount() {

		return expression.getColumnCount();
	}
}
