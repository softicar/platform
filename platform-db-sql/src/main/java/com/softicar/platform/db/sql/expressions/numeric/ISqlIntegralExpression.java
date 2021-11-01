package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.operations.SqlOperations;

public interface ISqlIntegralExpression<SELF, BOOL, VALUE> extends ISqlNumericExpression<SELF, BOOL, VALUE> {

	@Override
	default SELF divided(ISqlExpression0<VALUE> other) {

		return wrap(SqlOperations.INTEGRAL_DIVIDED.create(this, other));
	}

	default SELF bitAnd(VALUE value) {

		return wrap(SqlOperations.BIT_AND.create(this, literal(value)));
	}

	default SELF bitInverse(VALUE value) {

		return wrap(SqlOperations.BIT_INVERSE.create(this, literal(value)));
	}

	default SELF bitOr(VALUE value) {

		return wrap(SqlOperations.BIT_OR.create(this, literal(value)));
	}

	default SELF bitXor(VALUE value) {

		return wrap(SqlOperations.BIT_XOR.create(this, literal(value)));
	}
}
