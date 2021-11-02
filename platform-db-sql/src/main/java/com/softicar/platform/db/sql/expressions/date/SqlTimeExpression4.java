package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlTimeExpression4<T0, T1, T2, T3> extends SqlExpressionWrapper<Time> implements ISqlTimeExpression4<T0, T1, T2, T3> {
	public SqlTimeExpression4(ISqlExpression<Time> expression) {
		super(expression);
	}
}

