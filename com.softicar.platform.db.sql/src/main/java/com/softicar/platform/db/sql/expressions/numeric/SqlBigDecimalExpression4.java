package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;
import java.math.BigDecimal;

public class SqlBigDecimalExpression4<T0, T1, T2, T3> extends SqlExpressionWrapper<BigDecimal> implements ISqlBigDecimalExpression4<T0, T1, T2, T3> {
	public SqlBigDecimalExpression4(ISqlExpression<BigDecimal> expression) {
		super(expression);
	}
}

