package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlBooleanExpression4<T0, T1, T2, T3> extends SqlExpressionWrapper<Boolean> implements ISqlBooleanExpression4<T0, T1, T2, T3> {
	public SqlBooleanExpression4(ISqlExpression<Boolean> expression) {
		super(expression);
	}
}

