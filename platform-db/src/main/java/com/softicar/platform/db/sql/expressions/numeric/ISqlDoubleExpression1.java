package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression1;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression1;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlDoubleExpression1<T0> extends ISqlExpression1<Double, T0>, ISqlDoubleExpression<ISqlDoubleExpression1<T0>, ISqlBooleanExpression<T0>, ISqlLongExpression1<T0>> {
	@Override
	default ISqlDoubleExpression1<T0> wrap(ISqlExpression<Double> expression) {
		return new SqlDoubleExpression1<>(expression);
	}

	@Override
	default ISqlLongExpression1<T0> wrapLong(ISqlExpression<Long> expression) {
		return new SqlLongExpression1<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlStringExpression1<T0> castToString() {
		return new SqlStringExpression1<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	default ISqlFloatExpression1<T0> castToFloat() {
		return new SqlFloatExpression1<>(SqlOperations.NOP.create(SqlValueTypes.FLOAT, this));
	}

	default ISqlBigDecimalExpression1<T0> castToBigDecimal() {
		return new SqlBigDecimalExpression1<>(SqlOperations.NOP.create(SqlValueTypes.BIG_DECIMAL, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression2<T0, S0> isEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isNotEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isLess(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isLessEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isGreater(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isGreaterEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlDoubleExpression2<T0, S0> plus(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression2<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0> ISqlDoubleExpression2<T0, S0> minus(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression2<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0> ISqlDoubleExpression2<T0, S0> times(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression2<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0> ISqlDoubleExpression2<T0, S0> modulo(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression2<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0> ISqlDoubleExpression2<T0, S0> divided(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression2<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isEqual(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isNotEqual(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isLess(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isLessEqual(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isGreater(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isGreaterEqual(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlDoubleExpression3<T0, S0, S1> plus(ISqlExpression2<Double, S0, S1> other) {
		return new SqlDoubleExpression3<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0, S1> ISqlDoubleExpression3<T0, S0, S1> minus(ISqlExpression2<Double, S0, S1> other) {
		return new SqlDoubleExpression3<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0, S1> ISqlDoubleExpression3<T0, S0, S1> times(ISqlExpression2<Double, S0, S1> other) {
		return new SqlDoubleExpression3<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0, S1> ISqlDoubleExpression3<T0, S0, S1> modulo(ISqlExpression2<Double, S0, S1> other) {
		return new SqlDoubleExpression3<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0, S1> ISqlDoubleExpression3<T0, S0, S1> divided(ISqlExpression2<Double, S0, S1> other) {
		return new SqlDoubleExpression3<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}

	// -------------------------------- OPERATIONS 3 -------------------------------- //

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isEqual(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isNotEqual(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isLess(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isLessEqual(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isGreater(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isGreaterEqual(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlDoubleExpression4<T0, S0, S1, S2> plus(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlDoubleExpression4<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0, S1, S2> ISqlDoubleExpression4<T0, S0, S1, S2> minus(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlDoubleExpression4<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0, S1, S2> ISqlDoubleExpression4<T0, S0, S1, S2> times(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlDoubleExpression4<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0, S1, S2> ISqlDoubleExpression4<T0, S0, S1, S2> modulo(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlDoubleExpression4<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0, S1, S2> ISqlDoubleExpression4<T0, S0, S1, S2> divided(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlDoubleExpression4<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}
}

