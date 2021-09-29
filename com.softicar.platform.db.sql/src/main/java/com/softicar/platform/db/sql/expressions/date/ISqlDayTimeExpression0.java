package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression0;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression1;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression1;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression0;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression0;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlDayTimeExpression0 extends ISqlExpression0<DayTime>, ISqlDayTimeExpression<ISqlDayTimeExpression0, ISqlBooleanExpression0> {
	@Override
	default ISqlDayTimeExpression0 wrap(ISqlExpression<DayTime> expression) {
		return new SqlDayTimeExpression0(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlDayExpression0 castToDay() {
		return new SqlDayExpression0(SqlOperations.CAST_DATE.create(SqlValueTypes.DAY, this));
	}

	default ISqlStringExpression0 castToString() {
		return new SqlStringExpression0(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression1<S0> isEqual(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isNotEqual(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isLess(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isLessEqual(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isGreater(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression1<S0> isGreaterEqual(ISqlExpression1<DayTime, S0> other) {
		return new SqlBooleanExpression1<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isEqual(ISqlExpression2<DayTime, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isNotEqual(ISqlExpression2<DayTime, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isLess(ISqlExpression2<DayTime, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isLessEqual(ISqlExpression2<DayTime, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isGreater(ISqlExpression2<DayTime, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression2<S0, S1> isGreaterEqual(ISqlExpression2<DayTime, S0, S1> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 3 -------------------------------- //

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isEqual(ISqlExpression3<DayTime, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isNotEqual(ISqlExpression3<DayTime, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isLess(ISqlExpression3<DayTime, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isLessEqual(ISqlExpression3<DayTime, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isGreater(ISqlExpression3<DayTime, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression3<S0, S1, S2> isGreaterEqual(ISqlExpression3<DayTime, S0, S1, S2> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 4 -------------------------------- //

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isEqual(ISqlExpression4<DayTime, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isNotEqual(ISqlExpression4<DayTime, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isLess(ISqlExpression4<DayTime, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isLessEqual(ISqlExpression4<DayTime, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_LESS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isGreater(ISqlExpression4<DayTime, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2, S3> ISqlBooleanExpression4<S0, S1, S2, S3> isGreaterEqual(ISqlExpression4<DayTime, S0, S1, S2, S3> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_GREATER_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}
}

