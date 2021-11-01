package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlFloatExpression1<T0> extends SqlExpressionWrapper<Float> implements ISqlFloatExpression1<T0> {
	public SqlFloatExpression1(ISqlExpression<Float> expression) {
		super(expression);
	}
}

