package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlBooleanExpression2<T0, T1> extends SqlExpressionWrapper<Boolean> implements ISqlBooleanExpression2<T0, T1> {
	public SqlBooleanExpression2(ISqlExpression<Boolean> expression) {
		super(expression);
	}
}

