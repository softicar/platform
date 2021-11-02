package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression4;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression4;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlDayTimeExpression4<T0, T1, T2, T3> extends ISqlExpression4<DayTime, T0, T1, T2, T3>, ISqlDayTimeExpression<ISqlDayTimeExpression4<T0, T1, T2, T3>, ISqlBooleanExpression4<T0, T1, T2, T3>> {
	@Override
	default ISqlDayTimeExpression4<T0, T1, T2, T3> wrap(ISqlExpression<DayTime> expression) {
		return new SqlDayTimeExpression4<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlDayExpression4<T0, T1, T2, T3> castToDay() {
		return new SqlDayExpression4<>(SqlOperations.CAST_DATE.create(SqlValueTypes.DAY, this));
	}

	default ISqlStringExpression4<T0, T1, T2, T3> castToString() {
		return new SqlStringExpression4<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}
}

