package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDoubleExpression1<T0> extends SqlExpressionWrapper<Double> implements ISqlDoubleExpression1<T0> {
	public SqlDoubleExpression1(ISqlExpression<Double> expression) {
		super(expression);
	}
}

