package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.operations.SqlOperations;

public interface ISqlBooleanExpression3<T0, T1, T2> extends ISqlExpression3<Boolean, T0, T1, T2>, ISqlBooleanExpressionX<ISqlBooleanExpression3<T0, T1, T2>> {
	@Override
	default ISqlBooleanExpression3<T0, T1, T2> wrap(ISqlExpression<Boolean> expression) {
		return new SqlBooleanExpression3<>(expression);
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isEqual(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isNotEqual(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> and(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.AND.create(this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> or(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.OR.create(this, other));
	}
}

