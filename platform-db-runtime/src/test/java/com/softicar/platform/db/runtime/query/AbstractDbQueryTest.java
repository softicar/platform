package com.softicar.platform.db.runtime.query;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.query.builder.AbstractDbQuerySqlBuilder;
import com.softicar.platform.db.runtime.query.builder.IDbQuerySqlBuilder;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import org.junit.Test;

public class AbstractDbQueryTest extends AbstractTest {

	@Test
	public void testGetIdentifier() {

		String expectedIdentifier = TestQuery.class.getCanonicalName();

		TestQuery query = new TestQuery();
		DataTableIdentifier identifier = query.getIdentifier();

		assertEquals(expectedIdentifier, identifier.toString());
	}

	private static class TestQueryRow extends AbstractDbQueryRow<TestQueryRow> {

		protected TestQueryRow(IDbQuery<TestQueryRow> query) {

			super(query);
		}

		@Override
		protected TestQueryRow getThis() {

			return this;
		}
	}

	private static class TestQuery extends AbstractDbQuery<TestQueryRow> {

		@Override
		public TestQueryRow createRow(IDbSqlSelect select, DbResultSet resultSet) {

			throw new UnsupportedOperationException();
		}

		@Override
		public IDbQuerySqlBuilder createSqlBuilder() {

			return new TestQueryBuilder();
		}
	}

	private static class TestQueryBuilder extends AbstractDbQuerySqlBuilder {

		@Override
		protected void buildOriginalSelect() {

			SELECT();
			addIdentifier("x", DbTestObject.STRING_FIELD);
			addToken(SqlKeyword.AS);
			addIdentifier("text");
			FROM();
			addIdentifier(DbTestObject.TABLE);
			addToken(SqlKeyword.AS);
			addIdentifier("x");
			WHERE();
			addIdentifier("x", DbTestObject.ID_FIELD);
			addToken(SqlSymbol.EQUAL);
			addParameter(12345);
		}
	}
}
