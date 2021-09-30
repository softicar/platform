package com.softicar.platform.db.sql.expressions.array;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlByteArrayExpression3<T0, T1, T2> extends SqlExpressionWrapper<byte[]> implements ISqlByteArrayExpression3<T0, T1, T2> {
	public SqlByteArrayExpression3(ISqlExpression<byte[]> expression) {
		super(expression);
	}
}

