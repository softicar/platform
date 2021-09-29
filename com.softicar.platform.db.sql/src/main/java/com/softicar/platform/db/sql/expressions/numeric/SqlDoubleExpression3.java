package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDoubleExpression3<T0, T1, T2> extends SqlExpressionWrapper<Double> implements ISqlDoubleExpression3<T0, T1, T2> {
	public SqlDoubleExpression3(ISqlExpression<Double> expression) {
		super(expression);
	}
}

