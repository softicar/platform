package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlFloatExpression4<T0, T1, T2, T3> extends SqlExpressionWrapper<Float> implements ISqlFloatExpression4<T0, T1, T2, T3> {
	public SqlFloatExpression4(ISqlExpression<Float> expression) {
		super(expression);
	}
}

