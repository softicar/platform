package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDoubleExpression4<T0, T1, T2, T3> extends SqlExpressionWrapper<Double> implements ISqlDoubleExpression4<T0, T1, T2, T3> {
	public SqlDoubleExpression4(ISqlExpression<Double> expression) {
		super(expression);
	}
}

