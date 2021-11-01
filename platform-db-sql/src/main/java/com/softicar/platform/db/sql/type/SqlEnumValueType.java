package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for enumerations.
 *
 * @author Oliver Richers
 */
final class SqlEnumValueType<E extends Enum<E>> extends PrimitiveSqlValueType<E> {

	private final Class<E> enumClass;

	public SqlEnumValueType(Class<E> enumClass) {

		this.enumClass = enumClass;
	}

	@Override
	public E getValue(DbResultSet resultSet, int index) {

		return resultSet.getEnum(enumClass, index);
	}

	@Override
	public Class<E> getValueClass() {

		return enumClass;
	}

	@Override
	public int compare(E left, E right) {

		return SqlComparisons.compare(left, right);
	}
}
