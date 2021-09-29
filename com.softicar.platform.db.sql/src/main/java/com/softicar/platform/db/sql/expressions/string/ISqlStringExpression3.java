package com.softicar.platform.db.sql.expressions.string;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.numeric.ISqlIntegerExpression3;
import com.softicar.platform.db.sql.expressions.numeric.ISqlLongExpression3;
import com.softicar.platform.db.sql.expressions.numeric.SqlIntegerExpression3;
import com.softicar.platform.db.sql.expressions.numeric.SqlLongExpression3;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlStringExpression3<T0, T1, T2> extends ISqlExpression3<String, T0, T1, T2>, ISqlStringExpression<ISqlStringExpression3<T0, T1, T2>, ISqlBooleanExpression3<T0, T1, T2>> {
	@Override
	default ISqlStringExpression3<T0, T1, T2> wrap(ISqlExpression<String> expression) {
		return new SqlStringExpression3<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlIntegerExpression3<T0, T1, T2> castToInteger() {
		return new SqlIntegerExpression3<>(SqlOperations.CAST_SIGNED.create(SqlValueTypes.INTEGER, this));
	}

	default ISqlLongExpression3<T0, T1, T2> castToLong() {
		return new SqlLongExpression3<>(SqlOperations.CAST_SIGNED.create(SqlValueTypes.LONG, this));
	}

	// -------------------------------- UNARY OPERATIONS -------------------------------- //

	default ISqlIntegerExpression3<T0, T1, T2> length() {
		return new SqlIntegerExpression3<>(SqlOperations.LENGTH.create(SqlValueTypes.INTEGER, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isNotEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLess(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLessEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isGreater(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isGreaterEqual(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlStringExpression4<T0, T1, T2, S0> concat(ISqlExpression1<String, S0> other) {
		return new SqlStringExpression4<>(SqlOperations.CONCAT.create(this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLike(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isNotLike(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_LIKE.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isRegexp(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isNotRegexp(ISqlExpression1<String, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_REGEXP.create(SqlValueTypes.BOOLEAN, this, other));
	}
}

