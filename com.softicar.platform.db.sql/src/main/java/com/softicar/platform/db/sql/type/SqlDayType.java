package com.softicar.platform.db.sql.type;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for {@link Day}.
 *
 * @author Oliver Richers
 */
final class SqlDayType extends PrimitiveSqlValueType<Day> {

	@Override
	public Day getValue(DbResultSet resultSet, int index) {

		return resultSet.getDay(index);
	}

	@Override
	public Class<Day> getValueClass() {

		return Day.class;
	}

	@Override
	public int compare(Day left, Day right) {

		return SqlComparisons.compare(left, right);
	}
}
