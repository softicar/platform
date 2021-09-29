package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlFloatExpression2<T0, T1> extends SqlExpressionWrapper<Float> implements ISqlFloatExpression2<T0, T1> {
	public SqlFloatExpression2(ISqlExpression<Float> expression) {
		super(expression);
	}
}

