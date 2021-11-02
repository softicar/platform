package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlLongExpression3<T0, T1, T2> extends SqlExpressionWrapper<Long> implements ISqlLongExpression3<T0, T1, T2> {
	public SqlLongExpression3(ISqlExpression<Long> expression) {
		super(expression);
	}
}

