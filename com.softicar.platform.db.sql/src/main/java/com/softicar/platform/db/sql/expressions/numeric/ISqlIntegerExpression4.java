package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression4;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression4;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlIntegerExpression4<T0, T1, T2, T3> extends ISqlExpression4<Integer, T0, T1, T2, T3>, ISqlIntegerExpression<ISqlIntegerExpression4<T0, T1, T2, T3>, ISqlBooleanExpression4<T0, T1, T2, T3>> {
	@Override
	default ISqlIntegerExpression4<T0, T1, T2, T3> wrap(ISqlExpression<Integer> expression) {
		return new SqlIntegerExpression4<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlStringExpression4<T0, T1, T2, T3> castToString() {
		return new SqlStringExpression4<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	default ISqlFloatExpression4<T0, T1, T2, T3> castToFloat() {
		return new SqlFloatExpression4<>(SqlOperations.CAST_DECIMAL.create(SqlValueTypes.FLOAT, this));
	}

	default ISqlDoubleExpression4<T0, T1, T2, T3> castToDouble() {
		return new SqlDoubleExpression4<>(SqlOperations.CAST_DECIMAL.create(SqlValueTypes.DOUBLE, this));
	}

	default ISqlBigDecimalExpression4<T0, T1, T2, T3> castToBigDecimal() {
		return new SqlBigDecimalExpression4<>(SqlOperations.CAST_DECIMAL.create(SqlValueTypes.BIG_DECIMAL, this));
	}

	default ISqlLongExpression4<T0, T1, T2, T3> castToLong() {
		return new SqlLongExpression4<>(SqlOperations.NOP.create(SqlValueTypes.LONG, this));
	}
}

