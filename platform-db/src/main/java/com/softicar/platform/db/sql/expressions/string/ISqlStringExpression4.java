package com.softicar.platform.db.sql.expressions.string;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.numeric.ISqlIntegerExpression4;
import com.softicar.platform.db.sql.expressions.numeric.ISqlLongExpression4;
import com.softicar.platform.db.sql.expressions.numeric.SqlIntegerExpression4;
import com.softicar.platform.db.sql.expressions.numeric.SqlLongExpression4;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlStringExpression4<T0, T1, T2, T3> extends ISqlExpression4<String, T0, T1, T2, T3>, ISqlStringExpression<ISqlStringExpression4<T0, T1, T2, T3>, ISqlBooleanExpression4<T0, T1, T2, T3>> {
	@Override
	default ISqlStringExpression4<T0, T1, T2, T3> wrap(ISqlExpression<String> expression) {
		return new SqlStringExpression4<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlIntegerExpression4<T0, T1, T2, T3> castToInteger() {
		return new SqlIntegerExpression4<>(SqlOperations.CAST_SIGNED.create(SqlValueTypes.INTEGER, this));
	}

	default ISqlLongExpression4<T0, T1, T2, T3> castToLong() {
		return new SqlLongExpression4<>(SqlOperations.CAST_SIGNED.create(SqlValueTypes.LONG, this));
	}

	// -------------------------------- UNARY OPERATIONS -------------------------------- //

	default ISqlIntegerExpression4<T0, T1, T2, T3> length() {
		return new SqlIntegerExpression4<>(SqlOperations.LENGTH.create(SqlValueTypes.INTEGER, this));
	}
}

