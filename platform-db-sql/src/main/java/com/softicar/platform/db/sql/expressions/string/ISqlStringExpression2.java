package com.softicar.platform.db.sql.expressions.string;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.numeric.ISqlIntegerExpression2;
import com.softicar.platform.db.sql.expressions.numeric.ISqlLongExpression2;
import com.softicar.platform.db.sql.expressions.numeric.SqlIntegerExpression2;
import com.softicar.platform.db.sql.expressions.numeric.SqlLongExpression2;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlStringExpression2<T0, T1> extends ISqlExpression2<String, T0, T1>, ISqlStringExpression<ISqlStringExpression2<T0, T1>, ISqlBooleanExpression2<T0, T1>> {
	@Override
	default ISqlStringExpression2<T0, T1> wrap(ISqlExpression<String> expression) {
		return new SqlStringExpression2<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlIntegerExpression2<T0, T1> castToInteger() {
		return new SqlIntegerExpression2<>(SqlOperations.CAST_SIGNED.create(SqlValueTypes.INTEGER, this));
	}

	default ISqlLongExpression2<T0, T1> castToLong() {
		return new SqlLongExpression2<>(SqlOperations.CAST_SIGNED.create(SqlValueTypes.LONG, this));
	}

	// -------------------------------- UNARY OPERATIONS -------------------------------- //

	default ISqlIntegerExpression2<T0, T1> length() {
		return new SqlIntegerExpression2<>(SqlOperations.LENGTH.create(SqlValueTypes.INTEGER, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isNotEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isLess(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isLessEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isGreater(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isGreaterEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlStringExpression3<T0, T1, S0> concat(ISqlExpression1<String, S0> other) {
		return new SqlStringExpression3<>(SqlOperations.CONCAT.create(this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isLike(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isNotLike(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isRegexp(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isNotRegexp(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isEqual(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isNotEqual(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isLess(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isLessEqual(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isGreater(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isGreaterEqual(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlStringExpression4<T0, T1, S0, S1> concat(ISqlExpression2<String, S0, S1> other) {
		return new SqlStringExpression4<>(SqlOperations.CONCAT.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isLike(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isNotLike(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isRegexp(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isNotRegexp(ISqlExpression2<String, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}
}

