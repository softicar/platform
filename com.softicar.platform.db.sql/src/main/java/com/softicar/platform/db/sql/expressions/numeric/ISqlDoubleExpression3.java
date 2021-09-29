package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression3;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression3;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlDoubleExpression3<T0, T1, T2> extends ISqlExpression3<Double, T0, T1, T2>, ISqlDoubleExpression<ISqlDoubleExpression3<T0, T1, T2>, ISqlBooleanExpression3<T0, T1, T2>, ISqlLongExpression3<T0, T1, T2>> {
	@Override
	default ISqlDoubleExpression3<T0, T1, T2> wrap(ISqlExpression<Double> expression) {
		return new SqlDoubleExpression3<>(expression);
	}

	@Override
	default ISqlLongExpression3<T0, T1, T2> wrapLong(ISqlExpression<Long> expression) {
		return new SqlLongExpression3<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlStringExpression3<T0, T1, T2> castToString() {
		return new SqlStringExpression3<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	default ISqlFloatExpression3<T0, T1, T2> castToFloat() {
		return new SqlFloatExpression3<>(SqlOperations.NOP.create(SqlValueTypes.FLOAT, this));
	}

	default ISqlBigDecimalExpression3<T0, T1, T2> castToBigDecimal() {
		return new SqlBigDecimalExpression3<>(SqlOperations.NOP.create(SqlValueTypes.BIG_DECIMAL, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isNotEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLess(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLessEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isGreater(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isGreaterEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlDoubleExpression4<T0, T1, T2, S0> plus(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression4<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0> ISqlDoubleExpression4<T0, T1, T2, S0> minus(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression4<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0> ISqlDoubleExpression4<T0, T1, T2, S0> times(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression4<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0> ISqlDoubleExpression4<T0, T1, T2, S0> modulo(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression4<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0> ISqlDoubleExpression4<T0, T1, T2, S0> divided(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression4<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}
}

