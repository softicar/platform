package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlFloatExpression3<T0, T1, T2> extends SqlExpressionWrapper<Float> implements ISqlFloatExpression3<T0, T1, T2> {
	public SqlFloatExpression3(ISqlExpression<Float> expression) {
		super(expression);
	}
}

