package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for floats.
 *
 * @author Oliver Richers
 */
final class SqlFloatType extends PrimitiveSqlValueType<Float> {

	@Override
	public Float getValue(DbResultSet resultSet, int index) {

		return resultSet.getFloat(index);
	}

	@Override
	public Class<Float> getValueClass() {

		return Float.class;
	}

	@Override
	public int compare(Float left, Float right) {

		return SqlComparisons.compare(left, right);
	}
}
