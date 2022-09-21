package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlDecimalExpression<SELF, BOOL, LONG, VALUE> extends ISqlNumericExpression<SELF, BOOL, VALUE> {

	@Override
	default SELF divided(ISqlExpression0<VALUE> other) {

		return wrap(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}

	default SELF round(int precision) {

		return wrap(SqlOperations.ROUND_TO.create(this, Sql.literal(precision)));
	}

	default LONG ceil() {

		return wrapLong(SqlOperations.CEIL.create(SqlValueTypes.LONG, this));
	}

	default LONG floor() {

		return wrapLong(SqlOperations.FLOOR.create(SqlValueTypes.LONG, this));
	}

	default LONG round() {

		return wrapLong(SqlOperations.ROUND.create(SqlValueTypes.LONG, this));
	}

	LONG wrapLong(ISqlExpression<Long> expression);
}
