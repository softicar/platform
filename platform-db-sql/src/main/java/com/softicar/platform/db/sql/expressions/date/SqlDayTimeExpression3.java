package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlDayTimeExpression3<T0, T1, T2> extends SqlExpressionWrapper<DayTime> implements ISqlDayTimeExpression3<T0, T1, T2> {
	public SqlDayTimeExpression3(ISqlExpression<DayTime> expression) {
		super(expression);
	}
}

