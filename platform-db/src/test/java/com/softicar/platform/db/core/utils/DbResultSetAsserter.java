package com.softicar.platform.db.core.utils;

import com.softicar.platform.db.core.DbResultSet;
import java.util.function.Supplier;
import org.junit.Assert;

/**
 * Helper class to assert the content of a {@link DbResultSet}.
 *
 * @author Oliver Richers
 */
public class DbResultSetAsserter {

	private final DbResultSet resultSet;

	public DbResultSetAsserter(Supplier<DbResultSet> resultSetSupplier) {

		this.resultSet = resultSetSupplier.get();
	}

	public DbResultSetAsserter(DbResultSet resultSet) {

		this.resultSet = resultSet;
	}

	public DbResultSetAsserter assertNextRow() {

		boolean hasNext = resultSet.next();
		Assert.assertTrue("Unexpected end of result set.", hasNext);
		return this;
	}

	public DbResultSetAsserter assertEndOfResultSet() {

		boolean hasNext = resultSet.next();
		Assert.assertFalse("Result set has more rows than expected.", hasNext);
		return this;
	}

	public DbResultSetAsserter assertString(String columnName, String expectedValue) {

		String actualValue = resultSet.getString(columnName);
		Assert.assertEquals(expectedValue, actualValue);
		return this;
	}

	public DbResultSetAsserter assertInt(String columnName, int expectedValue) {

		int actualValue = resultSet.getInt(columnName);
		Assert.assertEquals(expectedValue, actualValue);
		return this;
	}

	public DbResultSetAsserter assertInteger(String columnName, Integer expectedValue) {

		Integer actualValue = resultSet.getInteger(columnName);
		Assert.assertEquals(expectedValue, actualValue);
		return this;
	}
}
