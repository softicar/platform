package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDayExpression2<T0, T1> extends SqlExpressionWrapper<Day> implements ISqlDayExpression2<T0, T1> {
	public SqlDayExpression2(ISqlExpression<Day> expression) {
		super(expression);
	}
}

