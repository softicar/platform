package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlBooleanExpression3<T0, T1, T2> extends SqlExpressionWrapper<Boolean> implements ISqlBooleanExpression3<T0, T1, T2> {
	public SqlBooleanExpression3(ISqlExpression<Boolean> expression) {
		super(expression);
	}
}

