package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for longs.
 *
 * @author Oliver Richers
 */
final class SqlLongType extends PrimitiveSqlValueType<Long> {

	@Override
	public Long getValue(DbResultSet resultSet, int index) {

		return resultSet.getLong(index);
	}

	@Override
	public Class<Long> getValueClass() {

		return Long.class;
	}

	@Override
	public int compare(Long left, Long right) {

		return SqlComparisons.compare(left, right);
	}
}
