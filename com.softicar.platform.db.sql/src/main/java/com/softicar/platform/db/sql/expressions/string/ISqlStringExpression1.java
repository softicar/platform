package com.softicar.platform.db.sql.expressions.string;

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
import com.softicar.platform.db.sql.expressions.numeric.ISqlIntegerExpression1;
import com.softicar.platform.db.sql.expressions.numeric.ISqlLongExpression1;
import com.softicar.platform.db.sql.expressions.numeric.SqlIntegerExpression1;
import com.softicar.platform.db.sql.expressions.numeric.SqlLongExpression1;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlStringExpression1<T0> extends ISqlExpression1<String, T0>, ISqlStringExpression<ISqlStringExpression1<T0>, ISqlBooleanExpression<T0>> {
	@Override
	default ISqlStringExpression1<T0> wrap(ISqlExpression<String> expression) {
		return new SqlStringExpression1<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlIntegerExpression1<T0> castToInteger() {
		return new SqlIntegerExpression1<>(SqlOperations.CAST_SIGNED.create(SqlValueTypes.INTEGER, this));
	}

	default ISqlLongExpression1<T0> castToLong() {
		return new SqlLongExpression1<>(SqlOperations.CAST_SIGNED.create(SqlValueTypes.LONG, this));
	}

	// -------------------------------- UNARY OPERATIONS -------------------------------- //

	default ISqlIntegerExpression1<T0> length() {
		return new SqlIntegerExpression1<>(SqlOperations.LENGTH.create(SqlValueTypes.INTEGER, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression2<T0, S0> isEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isNotEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isLess(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isLessEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isGreater(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isGreaterEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlStringExpression2<T0, S0> concat(ISqlExpression1<String, S0> other) {
		return new SqlStringExpression2<>(SqlOperations.CONCAT.create(this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isLike(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isNotLike(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isRegexp(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isNotRegexp(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isEqual(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isNotEqual(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isLess(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isLessEqual(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isGreater(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isGreaterEqual(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlStringExpression3<T0, S0, S1> concat(ISqlExpression2<String, S0, S1> other) {
		return new SqlStringExpression3<>(SqlOperations.CONCAT.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isLike(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isNotLike(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isRegexp(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isNotRegexp(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 3 -------------------------------- //

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isEqual(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isNotEqual(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isLess(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isLessEqual(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isGreater(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isGreaterEqual(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlStringExpression4<T0, S0, S1, S2> concat(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlStringExpression4<>(SqlOperations.CONCAT.create(this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isLike(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isNotLike(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isRegexp(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isNotRegexp(ISqlExpression3<String, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}
}

