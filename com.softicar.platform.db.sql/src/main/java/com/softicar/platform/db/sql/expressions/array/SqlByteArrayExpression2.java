package com.softicar.platform.db.sql.expressions.array;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlByteArrayExpression2<T0, T1> extends SqlExpressionWrapper<byte[]> implements ISqlByteArrayExpression2<T0, T1> {
	public SqlByteArrayExpression2(ISqlExpression<byte[]> expression) {
		super(expression);
	}
}

