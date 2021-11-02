package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression4;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression4;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlTimeExpression4<T0, T1, T2, T3> extends ISqlExpression4<Time, T0, T1, T2, T3>, ISqlTimeExpression<ISqlTimeExpression4<T0, T1, T2, T3>, ISqlBooleanExpression4<T0, T1, T2, T3>> {
	@Override
	default ISqlTimeExpression4<T0, T1, T2, T3> wrap(ISqlExpression<Time> expression) {
		return new SqlTimeExpression4<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlDayExpression4<T0, T1, T2, T3> castToDay() {
		return new SqlDayExpression4<>(SqlOperations.CAST_DATE.create(SqlValueTypes.DAY, this));
	}

	default ISqlDayTimeExpression4<T0, T1, T2, T3> castToDayTime() {
		return new SqlDayTimeExpression4<>(SqlOperations.CAST_DATETIME.create(SqlValueTypes.DAY_TIME, this));
	}

	default ISqlStringExpression4<T0, T1, T2, T3> castToString() {
		return new SqlStringExpression4<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}
}

