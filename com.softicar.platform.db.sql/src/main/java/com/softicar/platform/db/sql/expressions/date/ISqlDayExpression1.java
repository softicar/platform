package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Day;
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

public interface ISqlDayExpression1<T0> extends ISqlExpression1<Day, T0>, ISqlDayExpression<ISqlDayExpression1<T0>, ISqlBooleanExpression<T0>> {
	@Override
	default ISqlDayExpression1<T0> wrap(ISqlExpression<Day> expression) {
		return new SqlDayExpression1<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlDayTimeExpression1<T0> castToDayTime() {
		return new SqlDayTimeExpression1<>(SqlOperations.CAST_DATETIME.create(SqlValueTypes.DAY_TIME, this));
	}

	default ISqlStringExpression1<T0> castToString() {
		return new SqlStringExpression1<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression2<T0, S0> isEqual(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isNotEqual(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isLess(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isLessEqual(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isGreater(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isGreaterEqual(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isEqual(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isNotEqual(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isLess(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isLessEqual(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isGreater(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isGreaterEqual(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 3 -------------------------------- //

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isEqual(ISqlExpression3<Day, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isNotEqual(ISqlExpression3<Day, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isLess(ISqlExpression3<Day, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isLessEqual(ISqlExpression3<Day, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isGreater(ISqlExpression3<Day, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isGreaterEqual(ISqlExpression3<Day, S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}
}

