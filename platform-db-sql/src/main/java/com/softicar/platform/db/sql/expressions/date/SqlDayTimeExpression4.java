package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDayTimeExpression4<T0, T1, T2, T3> extends SqlExpressionWrapper<DayTime> implements ISqlDayTimeExpression4<T0, T1, T2, T3> {
	public SqlDayTimeExpression4(ISqlExpression<DayTime> expression) {
		super(expression);
	}
}

