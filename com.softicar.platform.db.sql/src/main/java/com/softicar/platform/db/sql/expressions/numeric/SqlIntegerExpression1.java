package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlIntegerExpression1<T0> extends SqlExpressionWrapper<Integer> implements ISqlIntegerExpression1<T0> {
	public SqlIntegerExpression1(ISqlExpression<Integer> expression) {
		super(expression);
	}
}

