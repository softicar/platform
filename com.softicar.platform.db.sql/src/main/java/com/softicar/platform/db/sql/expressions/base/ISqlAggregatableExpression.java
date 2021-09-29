package com.softicar.platform.db.sql.expressions.base;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.operations.SqlOperations;

public interface ISqlAggregatableExpression<SELF, BOOL, VALUE> extends ISqlComparableExpression<BOOL, VALUE> {

	SELF wrap(ISqlExpression<VALUE> expression);

	// -------------------- aggregate methods -------------------- //

	default SELF max() {

		return wrap(SqlOperations.MAX.create(this));
	}

	default SELF min() {

		return wrap(SqlOperations.MIN.create(this));
	}
}
