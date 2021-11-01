package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.operations.SqlOperations;

public interface ISqlBooleanExpression0 extends ISqlExpression0<Boolean>, ISqlBooleanExpressionX<ISqlBooleanExpression0> {
	@Override
	default ISqlBooleanExpression0 wrap(ISqlExpression<Boolean> expression) {
		return new SqlBooleanExpression0(expression);
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression1<S0> isEqual(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_EQUAL.create(this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isNotEqual(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_NOT_EQUAL.create(this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> and(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.AND.create(this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> or(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.OR.create(this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isEqual(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_EQUAL.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isNotEqual(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_EQUAL.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> and(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.AND.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> or(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.OR.create(this, other));
	}

	// -------------------------------- OPERATIONS 3 -------------------------------- //

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isEqual(ISqlExpression3<Boolean, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isNotEqual(ISqlExpression3<Boolean, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> and(ISqlExpression3<Boolean, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.AND.create(this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> or(ISqlExpression3<Boolean, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.OR.create(this, other));
	}

	// -------------------------------- OPERATIONS 4 -------------------------------- //

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isEqual(ISqlExpression4<Boolean, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isNotEqual(ISqlExpression4<Boolean, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> and(ISqlExpression4<Boolean, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.AND.create(this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> or(ISqlExpression4<Boolean, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.OR.create(this, other));
	}
}

