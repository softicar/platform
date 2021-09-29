package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for doubles.
 *
 * @author Oliver Richers
 */
final class SqlDoubleType extends PrimitiveSqlValueType<Double> {

	@Override
	public Double getValue(DbResultSet resultSet, int index) {

		return resultSet.getDouble(index);
	}

	@Override
	public Class<Double> getValueClass() {

		return Double.class;
	}

	@Override
	public int compare(Double left, Double right) {

		return SqlComparisons.compare(left, right);
	}
}
