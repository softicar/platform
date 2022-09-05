package com.softicar.platform.db.sql.expressions.base;

import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlComparableExpression<BOOL, VALUE> extends ISqlBasicExpression<BOOL, VALUE> {

	// -------------------- comparison with literal -------------------- //

	default BOOL isLess(VALUE other) {

		return isLess(literal(other));
	}

	default BOOL isLessEqual(VALUE other) {

		return isLessEqual(literal(other));
	}

	default BOOL isGreater(VALUE other) {

		return isGreater(literal(other));
	}

	default BOOL isGreaterEqual(VALUE other) {

		return isGreaterEqual(literal(other));
	}

	// -------------------- comparison with ISqlExpression0 -------------------- //

	default BOOL isLess(ISqlExpression0<VALUE> other) {

		return wrapBool(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default BOOL isLessEqual(ISqlExpression0<VALUE> other) {

		return wrapBool(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default BOOL isGreater(ISqlExpression0<VALUE> other) {

		return wrapBool(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default BOOL isGreaterEqual(ISqlExpression0<VALUE> other) {

		return wrapBool(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}
}
