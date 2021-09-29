package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlIntegerExpression2<T0, T1> extends SqlExpressionWrapper<Integer> implements ISqlIntegerExpression2<T0, T1> {
	public SqlIntegerExpression2(ISqlExpression<Integer> expression) {
		super(expression);
	}
}

