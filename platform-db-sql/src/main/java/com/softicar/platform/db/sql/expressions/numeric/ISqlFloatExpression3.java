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

public interface ISqlFloatExpression3<T0, T1, T2> extends ISqlExpression3<Float, T0, T1, T2>, ISqlFloatExpression<ISqlFloatExpression3<T0, T1, T2>, ISqlBooleanExpression3<T0, T1, T2>, ISqlLongExpression3<T0, T1, T2>> {
	@Override
	default ISqlFloatExpression3<T0, T1, T2> wrap(ISqlExpression<Float> expression) {
		return new SqlFloatExpression3<>(expression);
	}

	@Override
	default ISqlLongExpression3<T0, T1, T2> wrapLong(ISqlExpression<Long> expression) {
		return new SqlLongExpression3<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlStringExpression3<T0, T1, T2> castToString() {
		return new SqlStringExpression3<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	default ISqlDoubleExpression3<T0, T1, T2> castToDouble() {
		return new SqlDoubleExpression3<>(SqlOperations.NOP.create(SqlValueTypes.DOUBLE, this));
	}

	default ISqlBigDecimalExpression3<T0, T1, T2> castToBigDecimal() {
		return new SqlBigDecimalExpression3<>(SqlOperations.NOP.create(SqlValueTypes.BIG_DECIMAL, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isEqual(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isNotEqual(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLess(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLessEqual(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isGreater(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isGreaterEqual(ISqlExpression1<Float, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlFloatExpression4<T0, T1, T2, S0> plus(ISqlExpression1<Float, S0> other) {
		return new SqlFloatExpression4<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0> ISqlFloatExpression4<T0, T1, T2, S0> minus(ISqlExpression1<Float, S0> other) {
		return new SqlFloatExpression4<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0> ISqlFloatExpression4<T0, T1, T2, S0> times(ISqlExpression1<Float, S0> other) {
		return new SqlFloatExpression4<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0> ISqlFloatExpression4<T0, T1, T2, S0> modulo(ISqlExpression1<Float, S0> other) {
		return new SqlFloatExpression4<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0> ISqlFloatExpression4<T0, T1, T2, S0> divided(ISqlExpression1<Float, S0> other) {
		return new SqlFloatExpression4<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}
}

