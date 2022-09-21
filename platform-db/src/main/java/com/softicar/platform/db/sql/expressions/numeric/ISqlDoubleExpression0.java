package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression0;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression1;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression1;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression0;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression0;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlDoubleExpression0 extends ISqlExpression0<Double>, ISqlDoubleExpression<ISqlDoubleExpression0, ISqlBooleanExpression0, ISqlLongExpression0> {
	@Override
	default ISqlDoubleExpression0 wrap(ISqlExpression<Double> expression) {
		return new SqlDoubleExpression0(expression);
	}

	@Override
	default ISqlLongExpression0 wrapLong(ISqlExpression<Long> expression) {
		return new SqlLongExpression0(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlStringExpression0 castToString() {
		return new SqlStringExpression0(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	default ISqlFloatExpression0 castToFloat() {
		return new SqlFloatExpression0(SqlOperations.NOP.create(SqlValueTypes.FLOAT, this));
	}

	default ISqlBigDecimalExpression0 castToBigDecimal() {
		return new SqlBigDecimalExpression0(SqlOperations.NOP.create(SqlValueTypes.BIG_DECIMAL, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression1<S0> isEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isNotEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isLess(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isLessEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isGreater(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isGreaterEqual(ISqlExpression1<Double, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlDoubleExpression1<S0> plus(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression1<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0> ISqlDoubleExpression1<S0> minus(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression1<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0> ISqlDoubleExpression1<S0> times(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression1<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0> ISqlDoubleExpression1<S0> modulo(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression1<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0> ISqlDoubleExpression1<S0> divided(ISqlExpression1<Double, S0> other) {
		return new SqlDoubleExpression1<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isEqual(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isNotEqual(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isLess(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isLessEqual(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isGreater(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isGreaterEqual(ISqlExpression2<Double, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlDoubleExpression2<S0, S1> plus(ISqlExpression2<Double, S0, S1> other) {
		return new SqlDoubleExpression2<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0, S1> ISqlDoubleExpression2<S0, S1> minus(ISqlExpression2<Double, S0, S1> other) {
		return new SqlDoubleExpression2<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0, S1> ISqlDoubleExpression2<S0, S1> times(ISqlExpression2<Double, S0, S1> other) {
		return new SqlDoubleExpression2<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0, S1> ISqlDoubleExpression2<S0, S1> modulo(ISqlExpression2<Double, S0, S1> other) {
		return new SqlDoubleExpression2<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0, S1> ISqlDoubleExpression2<S0, S1> divided(ISqlExpression2<Double, S0, S1> other) {
		return new SqlDoubleExpression2<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}

	// -------------------------------- OPERATIONS 3 -------------------------------- //

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isEqual(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isNotEqual(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isLess(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isLessEqual(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isGreater(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isGreaterEqual(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlDoubleExpression3<S0, S1, S2> plus(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlDoubleExpression3<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0, S1, S2> ISqlDoubleExpression3<S0, S1, S2> minus(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlDoubleExpression3<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0, S1, S2> ISqlDoubleExpression3<S0, S1, S2> times(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlDoubleExpression3<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0, S1, S2> ISqlDoubleExpression3<S0, S1, S2> modulo(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlDoubleExpression3<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0, S1, S2> ISqlDoubleExpression3<S0, S1, S2> divided(ISqlExpression3<Double, S0, S1, S2> other) {
		return new SqlDoubleExpression3<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}

	// -------------------------------- OPERATIONS 4 -------------------------------- //

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isEqual(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isNotEqual(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isLess(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isLessEqual(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isGreater(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isGreaterEqual(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlDoubleExpression4<S0, S1, S2, S3> plus(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlDoubleExpression4<>(SqlOperations.PLUS.create(this, other));
	}

	default <S0, S1, S2, S3> ISqlDoubleExpression4<S0, S1, S2, S3> minus(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlDoubleExpression4<>(SqlOperations.MINUS.create(this, other));
	}

	default <S0, S1, S2, S3> ISqlDoubleExpression4<S0, S1, S2, S3> times(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlDoubleExpression4<>(SqlOperations.TIMES.create(this, other));
	}

	default <S0, S1, S2, S3> ISqlDoubleExpression4<S0, S1, S2, S3> modulo(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlDoubleExpression4<>(SqlOperations.MODULO.create(this, other));
	}

	default <S0, S1, S2, S3> ISqlDoubleExpression4<S0, S1, S2, S3> divided(ISqlExpression4<Double, S0, S1, S2, S3> other) {
		return new SqlDoubleExpression4<>(SqlOperations.DECIMAL_DIVIDED.create(this, other));
	}
}

