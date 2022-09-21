package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.base.ISqlAggregatableExpression;
import com.softicar.platform.db.sql.operations.SqlOperations;

public interface ISqlNumericExpression<SELF, BOOL, VALUE> extends ISqlAggregatableExpression<SELF, BOOL, VALUE> {

	default SELF abs() {

		return wrap(SqlOperations.ABS.create(this));
	}

	default SELF count() {

		return wrap(SqlOperations.COUNT.create(this));
	}

	default SELF neg() {

		return wrap(SqlOperations.NEGATE.create(this));
	}

	default SELF sum() {

		return wrap(SqlOperations.SUM.create(this));
	}

	default SELF plus(VALUE other) {

		return plus(literal(other));
	}

	default SELF minus(VALUE other) {

		return minus(literal(other));
	}

	default SELF times(VALUE other) {

		return times(literal(other));
	}

	default SELF modulo(VALUE other) {

		return modulo(literal(other));
	}

	default SELF divided(VALUE other) {

		return divided(literal(other));
	}

	default SELF plus(ISqlExpression0<VALUE> other) {

		return wrap(SqlOperations.PLUS.create(this, other));
	}

	default SELF minus(ISqlExpression0<VALUE> other) {

		return wrap(SqlOperations.MINUS.create(this, other));
	}

	default SELF times(ISqlExpression0<VALUE> other) {

		return wrap(SqlOperations.TIMES.create(this, other));
	}

	default SELF modulo(ISqlExpression0<VALUE> other) {

		return wrap(SqlOperations.MODULO.create(this, other));
	}

	SELF divided(ISqlExpression0<VALUE> other);
}
