package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlTimeExpression2<T0, T1> extends SqlExpressionWrapper<Time> implements ISqlTimeExpression2<T0, T1> {
	public SqlTimeExpression2(ISqlExpression<Time> expression) {
		super(expression);
	}
}

