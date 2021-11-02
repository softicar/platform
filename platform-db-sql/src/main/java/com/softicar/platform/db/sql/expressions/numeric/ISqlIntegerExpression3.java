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

public interface ISqlIntegerExpression3<T0, T1, T2> extends ISqlExpression3<Integer, T0, T1, T2>, ISqlIntegerExpression<ISqlIntegerExpression3<T0, T1, T2>, ISqlBooleanExpression3<T0, T1, T2>> {
	@Override
	default ISqlIntegerExpression3<T0, T1, T2> wrap(ISqlExpression<Integer> expression) {
		return new SqlIntegerExpression3<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlStringExpression3<T0, T1, T2> castToString() {
		return new SqlStringExpression3<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	default ISqlFloatExpression3<T0, T1, T2> castToFloat() {
		return new SqlFloatExpression3<>(SqlOperations.CAST_DECIMAL.create(SqlValueTypes.FLOAT, this));
	}

	default ISqlDoubleExpression3<T0, T1, T2> castToDouble() {
		return new SqlDoubleExpression3<>(SqlOperations.CAST_DECIMAL.create(SqlValueTypes.DOUBLE, this));
	}

	default ISqlBigDecimalExpression3<T0, T1, T2> castToBigDecimal() {
		return new SqlBigDecimalExpression3<>(SqlOperations.CAST_DECIMAL.create(SqlValueTypes.BIG_DECIMAL, this));
	}

	default ISqlLongExpression3<T0, T1, T2> castToLong() {
		return new SqlLongExpression3<>(SqlOperations.NOP.create(SqlValueTypes.LONG, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isEqual(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isNotEqual(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLess(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLessEqual(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isGreater(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isGreaterEqual(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlIntegerExpression4<T0, T1, T2, S0> plus(ISqlExpression1<Integer, S0> other) {
		return new SqlIntegerExpression4<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0> ISqlIntegerExpression4<T0, T1, T2, S0> minus(ISqlExpression1<Integer, S0> other) {
		return new SqlIntegerExpression4<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0> ISqlIntegerExpression4<T0, T1, T2, S0> times(ISqlExpression1<Integer, S0> other) {
		return new SqlIntegerExpression4<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0> ISqlIntegerExpression4<T0, T1, T2, S0> modulo(ISqlExpression1<Integer, S0> other) {
		return new SqlIntegerExpression4<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0> ISqlIntegerExpression4<T0, T1, T2, S0> divided(ISqlExpression1<Integer, S0> other) {
		return new SqlIntegerExpression4<>(SqlOperations.INTEGRAL_DIVIDED.create(this, other));
	}
}

