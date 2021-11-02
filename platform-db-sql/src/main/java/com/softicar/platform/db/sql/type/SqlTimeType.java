package com.softicar.platform.db.sql.type;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for strings.
 *
 * @author Oliver Richers
 */
final class SqlTimeType extends PrimitiveSqlValueType<Time> {

	@Override
	public Time getValue(DbResultSet resultSet, int index) {

		return resultSet.getTime(index);
	}

	@Override
	public Class<Time> getValueClass() {

		return Time.class;
	}

	@Override
	public int compare(Time left, Time right) {

		return SqlComparisons.compare(left, right);
	}
}
