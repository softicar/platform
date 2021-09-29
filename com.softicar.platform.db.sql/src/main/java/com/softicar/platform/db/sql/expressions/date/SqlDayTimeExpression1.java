package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDayTimeExpression1<T0> extends SqlExpressionWrapper<DayTime> implements ISqlDayTimeExpression1<T0> {
	public SqlDayTimeExpression1(ISqlExpression<DayTime> expression) {
		super(expression);
	}
}

