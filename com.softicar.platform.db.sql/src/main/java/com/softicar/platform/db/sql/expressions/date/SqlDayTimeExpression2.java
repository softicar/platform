package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDayTimeExpression2<T0, T1> extends SqlExpressionWrapper<DayTime> implements ISqlDayTimeExpression2<T0, T1> {
	public SqlDayTimeExpression2(ISqlExpression<DayTime> expression) {
		super(expression);
	}
}

