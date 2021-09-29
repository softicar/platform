package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDayExpression4<T0, T1, T2, T3> extends SqlExpressionWrapper<Day> implements ISqlDayExpression4<T0, T1, T2, T3> {
	public SqlDayExpression4(ISqlExpression<Day> expression) {
		super(expression);
	}
}

