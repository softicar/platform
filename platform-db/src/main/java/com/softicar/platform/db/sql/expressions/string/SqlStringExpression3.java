package com.softicar.platform.db.sql.expressions.string;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlStringExpression3<T0, T1, T2> extends SqlExpressionWrapper<String> implements ISqlStringExpression3<T0, T1, T2> {
	public SqlStringExpression3(ISqlExpression<String> expression) {
		super(expression);
	}
}

