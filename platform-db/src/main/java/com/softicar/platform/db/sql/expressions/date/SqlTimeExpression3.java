package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.helper.SqlExpressionWrapper;

public class SqlTimeExpression3<T0, T1, T2> extends SqlExpressionWrapper<Time> implements ISqlTimeExpression3<T0, T1, T2> {
	public SqlTimeExpression3(ISqlExpression<Time> expression) {
		super(expression);
	}
}

