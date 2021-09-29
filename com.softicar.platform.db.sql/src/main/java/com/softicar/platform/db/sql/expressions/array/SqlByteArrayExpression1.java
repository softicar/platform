package com.softicar.platform.db.sql.expressions.array;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlByteArrayExpression1<T0> extends SqlExpressionWrapper<byte[]> implements ISqlByteArrayExpression1<T0> {
	public SqlByteArrayExpression1(ISqlExpression<byte[]> expression) {
		super(expression);
	}
}

