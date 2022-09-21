package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlLongExpression4<T0, T1, T2, T3> extends SqlExpressionWrapper<Long> implements ISqlLongExpression4<T0, T1, T2, T3> {
	public SqlLongExpression4(ISqlExpression<Long> expression) {
		super(expression);
	}
}

