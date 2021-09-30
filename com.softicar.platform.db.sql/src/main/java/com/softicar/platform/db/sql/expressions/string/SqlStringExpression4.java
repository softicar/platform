package com.softicar.platform.db.sql.expressions.string;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlStringExpression4<T0, T1, T2, T3> extends SqlExpressionWrapper<String> implements ISqlStringExpression4<T0, T1, T2, T3> {
	public SqlStringExpression4(ISqlExpression<String> expression) {
		super(expression);
	}
}

