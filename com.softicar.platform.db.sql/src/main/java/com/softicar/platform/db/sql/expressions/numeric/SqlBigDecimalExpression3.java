package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;
import java.math.BigDecimal;

public class SqlBigDecimalExpression3<T0, T1, T2> extends SqlExpressionWrapper<BigDecimal> implements ISqlBigDecimalExpression3<T0, T1, T2> {
	public SqlBigDecimalExpression3(ISqlExpression<BigDecimal> expression) {
		super(expression);
	}
}

