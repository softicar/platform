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

public interface ISqlFloatExpression2<T0, T1> extends ISqlExpression2<Float, T0, T1>, ISqlFloatExpression<ISqlFloatExpression2<T0, T1>, ISqlBooleanExpression2<T0, T1>, ISqlLongExpression2<T0, T1>> {
	@Override
	default ISqlFloatExpression2<T0, T1> wrap(ISqlExpression<Float> expression) {
		return new SqlFloatExpression2<>(expression);
	}

	@Override
	default ISqlLongExpression2<T0, T1> wrapLong(ISqlExpression<Long> expression) {
		return new SqlLongExpression2<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlStringExpression2<T0, T1> castToString() {
		return new SqlStringExpression2<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	default ISqlDoubleExpression2<T0, T1> castToDouble() {
		return new SqlDoubleExpression2<>(SqlOperations.NOP.create(SqlValueTypes.DOUBLE, this));
	}

	default ISqlBigDecimalExpression2<T0, T1> castToBigDecimal() {
		return new SqlBigDecimalExpression2<>(SqlOperations.NOP.create(SqlValueTypes.BIG_DECIMAL, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isEqual(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isNotEqual(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isLess(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isLessEqual(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isGreater(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isGreaterEqual(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlFloatExpression3<T0, T1, S0> plus(ISqlExpression1<Float, S0> other) {
		return new SqlFloatExpression3<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0> ISqlFloatExpression3<T0, T1, S0> minus(ISqlExpression1<Float, S0> other) {
		return new SqlFloatExpression3<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0> ISqlFloatExpression3<T0, T1, S0> times(ISqlExpression1<Float, S0> other) {
		return new SqlFloatExpression3<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0> ISqlFloatExpression3<T0, T1, S0> modulo(ISqlExpression1<Float, S0> other) {
		return new SqlFloatExpression3<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0> ISqlFloatExpression3<T0, T1, S0> divided(ISqlExpression1<Float, S0> other) {
		return new SqlFloatExpression3<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isEqual(ISqlExpression2<Float, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isNotEqual(ISqlExpression2<Float, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isLess(ISqlExpression2<Float, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isLessEqual(ISqlExpression2<Float, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isGreater(ISqlExpression2<Float, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isGreaterEqual(ISqlExpression2<Float, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlFloatExpression4<T0, T1, S0, S1> plus(ISqlExpression2<Float, S0, S1> other) {
		return new SqlFloatExpression4<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0, S1> ISqlFloatExpression4<T0, T1, S0, S1> minus(ISqlExpression2<Float, S0, S1> other) {
		return new SqlFloatExpression4<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0, S1> ISqlFloatExpression4<T0, T1, S0, S1> times(ISqlExpression2<Float, S0, S1> other) {
		return new SqlFloatExpression4<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0, S1> ISqlFloatExpression4<T0, T1, S0, S1> modulo(ISqlExpression2<Float, S0, S1> other) {
		return new SqlFloatExpression4<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0, S1> ISqlFloatExpression4<T0, T1, S0, S1> divided(ISqlExpression2<Float, S0, S1> other) {
		return new SqlFloatExpression4<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}
}

