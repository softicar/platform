package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.expressions.base.ISqlAggregatableExpression;
import com.softicar.platform.db.sql.operations.SqlOperations;

public interface ISqlBooleanExpressionX<SELF> extends ISqlAggregatableExpression<SELF, SELF, Boolean> {

//	@Override
//	default SELF wrapBool(ISqlExpression<Boolean> value) {
//
//		return wrap(value);
//	}

	default SELF isTrue() {

		return wrap(this);
	}

	default SELF isFalse() {

		return not();
	}

	default SELF not() {

		return wrap(SqlOperations.NOT.create(this));
	}

	default SELF and(Boolean other) {

		return and(Sql.literal(other));
	}

	default SELF or(Boolean other) {

		return or(Sql.literal(other));
	}

	default SELF and(ISqlBooleanExpression0 other) {

		return wrap(SqlOperations.AND.create(this, other));
	}

	default SELF or(ISqlBooleanExpression0 other) {

		return wrap(SqlOperations.OR.create(this, other));
	}
}
