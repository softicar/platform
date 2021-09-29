package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression2;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression2;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlIntegerExpression2<T0, T1> extends ISqlExpression2<Integer, T0, T1>, ISqlIntegerExpression<ISqlIntegerExpression2<T0, T1>, ISqlBooleanExpression2<T0, T1>> {
	@Override
	default ISqlIntegerExpression2<T0, T1> wrap(ISqlExpression<Integer> expression) {
		return new SqlIntegerExpression2<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlStringExpression2<T0, T1> castToString() {
		return new SqlStringExpression2<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	default ISqlFloatExpression2<T0, T1> castToFloat() {
		return new SqlFloatExpression2<>(SqlOperations.CAST_DECIMAL.create(SqlValueTypes.FLOAT, this));
	}

	default ISqlDoubleExpression2<T0, T1> castToDouble() {
		return new SqlDoubleExpression2<>(SqlOperations.CAST_DECIMAL.create(SqlValueTypes.DOUBLE, this));
	}

	default ISqlBigDecimalExpression2<T0, T1> castToBigDecimal() {
		return new SqlBigDecimalExpression2<>(SqlOperations.CAST_DECIMAL.create(SqlValueTypes.BIG_DECIMAL, this));
	}

	default ISqlLongExpression2<T0, T1> castToLong() {
		return new SqlLongExpression2<>(SqlOperations.NOP.create(SqlValueTypes.LONG, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isEqual(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isNotEqual(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isLess(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isLessEqual(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isGreater(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isGreaterEqual(ISqlExpression1<Integer, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlIntegerExpression3<T0, T1, S0> plus(ISqlExpression1<Integer, S0> other) {
		return new SqlIntegerExpression3<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0> ISqlIntegerExpression3<T0, T1, S0> minus(ISqlExpression1<Integer, S0> other) {
		return new SqlIntegerExpression3<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0> ISqlIntegerExpression3<T0, T1, S0> times(ISqlExpression1<Integer, S0> other) {
		return new SqlIntegerExpression3<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0> ISqlIntegerExpression3<T0, T1, S0> modulo(ISqlExpression1<Integer, S0> other) {
		return new SqlIntegerExpression3<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0> ISqlIntegerExpression3<T0, T1, S0> divided(ISqlExpression1<Integer, S0> other) {
		return new SqlIntegerExpression3<>(SqlOperations.INTEGRAL_DIVIDED.create(this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isEqual(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isNotEqual(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isLess(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isLessEqual(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isGreater(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isGreaterEqual(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlIntegerExpression4<T0, T1, S0, S1> plus(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlIntegerExpression4<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0, S1> ISqlIntegerExpression4<T0, T1, S0, S1> minus(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlIntegerExpression4<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0, S1> ISqlIntegerExpression4<T0, T1, S0, S1> times(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlIntegerExpression4<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0, S1> ISqlIntegerExpression4<T0, T1, S0, S1> modulo(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlIntegerExpression4<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0, S1> ISqlIntegerExpression4<T0, T1, S0, S1> divided(ISqlExpression2<Integer, S0, S1> other) {
		return new SqlIntegerExpression4<>(SqlOperations.INTEGRAL_DIVIDED.create(this, other));
	}
}

