package com.softicar.platform.db.sql.expressions.array;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlByteArrayExpression4<T0, T1, T2, T3> extends SqlExpressionWrapper<byte[]> implements ISqlByteArrayExpression4<T0, T1, T2, T3> {
	public SqlByteArrayExpression4(ISqlExpression<byte[]> expression) {
		super(expression);
	}
}

