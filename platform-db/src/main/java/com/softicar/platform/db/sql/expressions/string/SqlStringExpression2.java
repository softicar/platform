package com.softicar.platform.db.sql.expressions.string;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlStringExpression2<T0, T1> extends SqlExpressionWrapper<String> implements ISqlStringExpression2<T0, T1> {
	public SqlStringExpression2(ISqlExpression<String> expression) {
		super(expression);
	}
}

