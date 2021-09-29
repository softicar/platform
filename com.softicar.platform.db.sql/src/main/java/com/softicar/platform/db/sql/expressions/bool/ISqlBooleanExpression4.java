package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;

public interface ISqlBooleanExpression4<T0, T1, T2, T3> extends ISqlExpression4<Boolean, T0, T1, T2, T3>, ISqlBooleanExpressionX<ISqlBooleanExpression4<T0, T1, T2, T3>> {
	@Override
	default ISqlBooleanExpression4<T0, T1, T2, T3> wrap(ISqlExpression<Boolean> expression) {
		return new SqlBooleanExpression4<>(expression);
	}
}

