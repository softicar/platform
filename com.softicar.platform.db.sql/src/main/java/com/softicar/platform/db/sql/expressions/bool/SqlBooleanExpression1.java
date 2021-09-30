package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlBooleanExpression1<T0> extends SqlExpressionWrapper<Boolean> implements ISqlBooleanExpression1<T0> {
	public SqlBooleanExpression1(ISqlExpression<Boolean> expression) {
		super(expression);
	}
}

