package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;

/**
 * Implementation of {@link ISqlValueType} for byte arrays.
 *
 * @author Oliver Richers
 */
final class SqlByteArrayType extends PrimitiveSqlValueType<byte[]> {

	@Override
	public byte[] getValue(DbResultSet resultSet, int index) {

		return resultSet.getBytes(index);
	}

	@Override
	public Class<byte[]> getValueClass() {

		return byte[].class;
	}

	@Override
	public int compare(byte[] left, byte[] right) {

		return SqlComparisons.compare(left, right);
	}
}
