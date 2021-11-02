package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for strings.
 *
 * @author Oliver Richers
 */
final class SqlStringType extends PrimitiveSqlValueType<String> {

	@Override
	public String getValue(DbResultSet resultSet, int index) {

		return resultSet.getString(index);
	}

	@Override
	public Class<String> getValueClass() {

		return String.class;
	}

	@Override
	public int compare(String left, String right) {

		return SqlComparisons.compare(left, right);
	}
}
