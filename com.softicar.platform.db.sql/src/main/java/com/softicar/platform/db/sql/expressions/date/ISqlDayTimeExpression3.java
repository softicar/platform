package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression3;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression3;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlDayTimeExpression3<T0, T1, T2> extends ISqlExpression3<DayTime, T0, T1, T2>, ISqlDayTimeExpression<ISqlDayTimeExpression3<T0, T1, T2>, ISqlBooleanExpression3<T0, T1, T2>> {
	@Override
	default ISqlDayTimeExpression3<T0, T1, T2> wrap(ISqlExpression<DayTime> expression) {
		return new SqlDayTimeExpression3<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlDayExpression3<T0, T1, T2> castToDay() {
		return new SqlDayExpression3<>(SqlOperations.CAST_DATE.create(SqlValueTypes.DAY, this));
	}

	default ISqlStringExpression3<T0, T1, T2> castToString() {
		return new SqlStringExpression3<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isEqual(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isNotEqual(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLess(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isLessEqual(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isGreater(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isGreaterEqual(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}
}

