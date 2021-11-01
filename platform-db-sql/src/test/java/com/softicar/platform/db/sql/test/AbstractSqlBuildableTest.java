package com.softicar.platform.db.sql.test;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuildable;
import com.softicar.platform.db.sql.statement.builder.clause.SqlClauseBuilder;
import java.util.Arrays;
import org.junit.Assert;
import org.mockito.Mockito;

public abstract class AbstractSqlBuildableTest extends Assert {

	private final SqlStatementBuilder statementBuilder;
	private final SqlClauseBuilder builder;

	public AbstractSqlBuildableTest() {

		ISqlTable<?> table = Mockito.mock(ISqlTable.class);
		this.statementBuilder = new SqlStatementBuilder(DbConnections.getServerQuirks(), table, "");
		this.builder = new SqlClauseBuilder(statementBuilder);
	}

	public void setAlias(String alias) {

		statementBuilder.setAlias(alias);
	}

	public void build(ISqlClauseBuildable expression) {

		expression.build(builder);
	}

	public void assertParameters(Object...expected) {

		assertEquals(Arrays.asList(expected), builder.getParameters());
	}

	public void assertText(String expected) {

		assertEquals(expected, builder.getText());
	}

	public void assertText(String expectedFormat, Object...args) {

		assertText(String.format(expectedFormat, args));
	}
}
