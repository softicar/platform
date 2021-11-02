package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlLongExpression1<T0> extends SqlExpressionWrapper<Long> implements ISqlLongExpression1<T0> {
	public SqlLongExpression1(ISqlExpression<Long> expression) {
		super(expression);
	}
}

