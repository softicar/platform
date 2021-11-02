package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlTimeExpression1<T0> extends SqlExpressionWrapper<Time> implements ISqlTimeExpression1<T0> {
	public SqlTimeExpression1(ISqlExpression<Time> expression) {
		super(expression);
	}
}

