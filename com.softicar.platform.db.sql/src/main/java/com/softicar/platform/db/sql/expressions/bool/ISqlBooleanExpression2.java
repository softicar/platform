package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.operations.SqlOperations;

public interface ISqlBooleanExpression2<T0, T1> extends ISqlExpression2<Boolean, T0, T1>, ISqlBooleanExpressionX<ISqlBooleanExpression2<T0, T1>> {
	@Override
	default ISqlBooleanExpression2<T0, T1> wrap(ISqlExpression<Boolean> expression) {
		return new SqlBooleanExpression2<>(expression);
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isEqual(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isNotEqual(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> and(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.AND.create(this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> or(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.OR.create(this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isEqual(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isNotEqual(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> and(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.AND.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> or(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.OR.create(this, other));
	}
}

