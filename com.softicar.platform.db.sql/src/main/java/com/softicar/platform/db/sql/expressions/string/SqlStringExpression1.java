package com.softicar.platform.db.sql.expressions.string;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlStringExpression1<T0> extends SqlExpressionWrapper<String> implements ISqlStringExpression1<T0> {
	public SqlStringExpression1(ISqlExpression<String> expression) {
		super(expression);
	}
}

