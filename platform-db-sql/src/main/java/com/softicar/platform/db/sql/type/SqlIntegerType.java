package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for integers.
 *
 * @author Oliver Richers
 */
final class SqlIntegerType extends PrimitiveSqlValueType<Integer> {

	@Override
	public Integer getValue(DbResultSet resultSet, int index) {

		return resultSet.getInteger(index);
	}

	@Override
	public Class<Integer> getValueClass() {

		return Integer.class;
	}

	@Override
	public int compare(Integer left, Integer right) {

		return SqlComparisons.compare(left, right);
	}
}
