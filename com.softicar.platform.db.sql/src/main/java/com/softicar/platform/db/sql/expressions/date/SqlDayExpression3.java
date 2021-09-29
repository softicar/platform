package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDayExpression3<T0, T1, T2> extends SqlExpressionWrapper<Day> implements ISqlDayExpression3<T0, T1, T2> {
	public SqlDayExpression3(ISqlExpression<Day> expression) {
		super(expression);
	}
}

