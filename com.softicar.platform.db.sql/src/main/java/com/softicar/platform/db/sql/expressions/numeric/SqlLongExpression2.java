package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlLongExpression2<T0, T1> extends SqlExpressionWrapper<Long> implements ISqlLongExpression2<T0, T1> {
	public SqlLongExpression2(ISqlExpression<Long> expression) {
		super(expression);
	}
}

