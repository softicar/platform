package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.operations.SqlOperations;

public interface ISqlBooleanExpression1<T0> extends ISqlBooleanExpression<T0>, ISqlBooleanExpressionX<ISqlBooleanExpression<T0>> {
	@Override
	default ISqlBooleanExpression1<T0> wrap(ISqlExpression<Boolean> expression) {
		return new SqlBooleanExpression1<>(expression);
	}

	@Override
	default ISqlBooleanExpression1<T0> and(ISqlBooleanExpression<T0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.AND.create(this, other).setFixTable(true));
	}

	@Override
	default ISqlBooleanExpression1<T0> or(ISqlBooleanExpression<T0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.OR.create(this, other).setFixTable(true));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression2<T0, S0> isEqual(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_EQUAL.create(this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isNotEqual(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_EQUAL.create(this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> and(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.AND.create(this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> or(ISqlExpression1<Boolean, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.OR.create(this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isEqual(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isNotEqual(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> and(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.AND.create(this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> or(ISqlExpression2<Boolean, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.OR.create(this, other));
	}

	// -------------------------------- OPERATIONS 3 -------------------------------- //

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isEqual(ISqlExpression3<Boolean, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isNotEqual(ISqlExpression3<Boolean, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> and(ISqlExpression3<Boolean, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.AND.create(this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> or(ISqlExpression3<Boolean, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.OR.create(this, other));
	}
}

