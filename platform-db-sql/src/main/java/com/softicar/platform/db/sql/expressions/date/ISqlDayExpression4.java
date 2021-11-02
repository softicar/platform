package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression4;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression4;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlDayExpression4<T0, T1, T2, T3> extends ISqlExpression4<Day, T0, T1, T2, T3>, ISqlDayExpression<ISqlDayExpression4<T0, T1, T2, T3>, ISqlBooleanExpression4<T0, T1, T2, T3>> {
	@Override
	default ISqlDayExpression4<T0, T1, T2, T3> wrap(ISqlExpression<Day> expression) {
		return new SqlDayExpression4<>(expression);
	}

	// -------------------------------- CASTS -------------------------------- //

	default ISqlDayTimeExpression4<T0, T1, T2, T3> castToDayTime() {
		return new SqlDayTimeExpression4<>(SqlOperations.CAST_DATETIME.create(SqlValueTypes.DAY_TIME, this));
	}

	default ISqlStringExpression4<T0, T1, T2, T3> castToString() {
		return new SqlStringExpression4<>(SqlOperations.CAST_CHAR.create(SqlValueTypes.STRING, this));
	}
}

