package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlIntegerExpression3<T0, T1, T2> extends SqlExpressionWrapper<Integer> implements ISqlIntegerExpression3<T0, T1, T2> {
	public SqlIntegerExpression3(ISqlExpression<Integer> expression) {
		super(expression);
	}
}

