package com.softicar.platform.db.sql.statement;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.base.AbstractSqlInsert;
import com.softicar.platform.db.sql.test.SqlTestEntity;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;

public class SqlTestInsert extends AbstractSqlInsert<SqlTestEntity> {

	protected SqlTestInsert(ISqlTable<SqlTestEntity> table) {

		super(table);
	}

	@Override
	public int execute() {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<Integer> executeMultiInsert() {

		throw new UnsupportedOperationException();
	}

	@Override
	public void executeWithoutIdGeneration() {

		throw new UnsupportedOperationException();
	}

	public void assertSqlText(String expectedText) {

		Assert.assertEquals(expectedText, getSqlText());
	}

	public void assertSqlParameters(Object...expectedParameters) {

		Assert.assertEquals(Arrays.asList(expectedParameters), getSqlParameters());
	}
}
