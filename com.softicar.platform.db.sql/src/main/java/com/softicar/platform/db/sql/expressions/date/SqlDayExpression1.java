package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDayExpression1<T0> extends SqlExpressionWrapper<Day> implements ISqlDayExpression1<T0> {
	public SqlDayExpression1(ISqlExpression<Day> expression) {
		super(expression);
	}
}

