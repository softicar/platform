package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;
import java.math.BigDecimal;

public class SqlBigDecimalExpression2<T0, T1> extends SqlExpressionWrapper<BigDecimal> implements ISqlBigDecimalExpression2<T0, T1> {
	public SqlBigDecimalExpression2(ISqlExpression<BigDecimal> expression) {
		super(expression);
	}
}

