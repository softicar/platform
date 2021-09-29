package com.softicar.platform.db.sql.type;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for {@link DayTime}.
 *
 * @author Oliver Richers
 */
final class SqlDayTimeType extends PrimitiveSqlValueType<DayTime> {

	@Override
	public DayTime getValue(DbResultSet resultSet, int index) {

		return resultSet.getDayTime(index);
	}

	@Override
	public Class<DayTime> getValueClass() {

		return DayTime.class;
	}

	@Override
	public int compare(DayTime left, DayTime right) {

		return SqlComparisons.compare(left, right);
	}
}
