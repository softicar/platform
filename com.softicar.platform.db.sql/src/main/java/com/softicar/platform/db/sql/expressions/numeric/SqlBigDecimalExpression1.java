package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;
import java.math.BigDecimal;

public class SqlBigDecimalExpression1<T0> extends SqlExpressionWrapper<BigDecimal> implements ISqlBigDecimalExpression1<T0> {
	public SqlBigDecimalExpression1(ISqlExpression<BigDecimal> expression) {
		super(expression);
	}
}

