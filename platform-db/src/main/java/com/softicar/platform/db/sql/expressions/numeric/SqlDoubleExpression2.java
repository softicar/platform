package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDoubleExpression2<T0, T1> extends SqlExpressionWrapper<Double> implements ISqlDoubleExpression2<T0, T1> {
	public SqlDoubleExpression2(ISqlExpression<Double> expression) {
		super(expression);
	}
}

