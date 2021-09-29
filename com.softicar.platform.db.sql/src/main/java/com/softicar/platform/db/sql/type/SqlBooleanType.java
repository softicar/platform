package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for booleans.
 *
 * @author Oliver Richers
 */
final class SqlBooleanType extends PrimitiveSqlValueType<Boolean> {

	@Override
	public Boolean getValue(DbResultSet resultSet, int index) {

		return resultSet.getBoolean(index);
	}

	@Override
	public Class<Boolean> getValueClass() {

		return Boolean.class;
	}

	@Override
	public int compare(Boolean left, Boolean right) {

		return SqlComparisons.compare(left, right);
	}
}
