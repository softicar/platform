package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression2;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression2;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlDayExpression2<T0, T1> extends ISqlExpression2<Day, T0, T1>, ISqlDayExpression<ISqlDayExpression2<T0, T1>, ISqlBooleanExpression2<T0, T1>> {
	@Override
	default ISqlDayExpression2<T0, T1> wrap(ISqlExpression<Day> expression) {
		return new SqlDayExpression2<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlDayTimeExpression2<T0, T1> castToDayTime() {
		return new SqlDayTimeExpression2<>(SqlOperations.CAST_DATETIME.create(SqlValueTypes.DAY_TIME, this));
	}

	default ISqlStringExpression2<T0, T1> castToString() {
		return new SqlStringExpression2<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isEqual(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isNotEqual(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isLess(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isLessEqual(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isGreater(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression3<T0, T1, S0> isGreaterEqual(ISqlExpression1<Day, S0> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isEqual(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isNotEqual(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isLess(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isLessEqual(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isGreater(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression4<T0, T1, S0, S1> isGreaterEqual(ISqlExpression2<Day, S0, S1> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}
}

