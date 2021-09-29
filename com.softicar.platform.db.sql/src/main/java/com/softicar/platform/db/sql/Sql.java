package com.softicar.platform.db.sql;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression0;
import com.softicar.platform.db.sql.expressions.date.SqlDayExpression0;
import com.softicar.platform.db.sql.expressions.date.SqlDayTimeExpression0;
import com.softicar.platform.db.sql.expressions.helper.SqlLiteralExpression;
import com.softicar.platform.db.sql.expressions.numeric.SqlBigDecimalExpression0;
import com.softicar.platform.db.sql.expressions.numeric.SqlDoubleExpression0;
import com.softicar.platform.db.sql.expressions.numeric.SqlFloatExpression0;
import com.softicar.platform.db.sql.expressions.numeric.SqlIntegerExpression0;
import com.softicar.platform.db.sql.expressions.numeric.SqlLongExpression0;
import com.softicar.platform.db.sql.expressions.string.SqlStringExpression0;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.selects.SqlSelect0_0;
import com.softicar.platform.db.sql.selects.SqlSelect0_1;
import com.softicar.platform.db.sql.selects.SqlSelect1_0;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.math.BigDecimal;

public class Sql {
	// -------------------------------- select -------------------------------- //

	public static SqlSelect0_0 select() {

		return new SqlSelect0_0();
	}

	public static <V0> SqlSelect0_1<V0> select(ISqlExpression0<V0> expression) {

		return select().select(expression);
	}

	// -------------------------------- from -------------------------------- //

	public static <T0> SqlSelect1_0<T0> from(ISqlTable<T0> table) {

		return select().from(table);
	}

	// -------------------------------- literal -------------------------------- //

	public static SqlBooleanExpression0 literal(Boolean value) {

		return new SqlBooleanExpression0(SqlLiteralExpression.create(SqlValueTypes.BOOLEAN, value));
	}

	public static SqlStringExpression0 literal(String value) {

		return new SqlStringExpression0(SqlLiteralExpression.create(SqlValueTypes.STRING, value));
	}

	public static SqlIntegerExpression0 literal(Integer value) {

		return new SqlIntegerExpression0(SqlLiteralExpression.create(SqlValueTypes.INTEGER, value));
	}

	public static SqlLongExpression0 literal(Long value) {

		return new SqlLongExpression0(SqlLiteralExpression.create(SqlValueTypes.LONG, value));
	}

	public static SqlFloatExpression0 literal(Float value) {

		return new SqlFloatExpression0(SqlLiteralExpression.create(SqlValueTypes.FLOAT, value));
	}

	public static SqlDoubleExpression0 literal(Double value) {

		return new SqlDoubleExpression0(SqlLiteralExpression.create(SqlValueTypes.DOUBLE, value));
	}

	public static SqlBigDecimalExpression0 literal(BigDecimal value) {

		return new SqlBigDecimalExpression0(SqlLiteralExpression.create(SqlValueTypes.BIG_DECIMAL, value));
	}

	public static SqlDayExpression0 literal(Day value) {

		return new SqlDayExpression0(SqlLiteralExpression.create(SqlValueTypes.DAY, value));
	}

	public static SqlDayTimeExpression0 literal(DayTime value) {

		return new SqlDayTimeExpression0(SqlLiteralExpression.create(SqlValueTypes.DAY_TIME, value));
	}

	// -------------------------------- common -------------------------------- //

	public static ISqlExpression0<DayTime> now() {

		return new SqlDayTimeExpression0(SqlOperations.NOW.create(SqlValueTypes.DAY_TIME));
	}

	public static ISqlExpression0<Integer> count() {

		return new SqlIntegerExpression0(SqlOperations.COUNT_ALL.create(SqlValueTypes.INTEGER));
	}
}

