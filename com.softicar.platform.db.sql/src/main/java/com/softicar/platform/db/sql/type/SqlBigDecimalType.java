package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.utils.SqlComparisons;
import java.math.BigDecimal;

/**
 * Implementation of {@link ISqlValueType} for {@link BigDecimal}.
 *
 * @author Oliver Richers
 */
final class SqlBigDecimalType extends PrimitiveSqlValueType<BigDecimal> {

	@Override
	public BigDecimal getValue(DbResultSet resultSet, int index) {

		return resultSet.getBigDecimal(index);
	}

	@Override
	public Class<BigDecimal> getValueClass() {

		return BigDecimal.class;
	}

	@Override
	public int compare(BigDecimal left, BigDecimal right) {

		return SqlComparisons.compare(left, right);
	}
}
