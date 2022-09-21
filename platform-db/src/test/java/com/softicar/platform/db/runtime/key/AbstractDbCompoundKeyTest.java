package com.softicar.platform.db.runtime.key;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuildable;
import com.softicar.platform.db.sql.statement.builder.clause.SqlClauseBuilder;
import java.util.Arrays;
import org.mockito.Mockito;

public class AbstractDbCompoundKeyTest extends AbstractTest {

	private final SqlStatementBuilder statementBuilder;
	private final SqlClauseBuilder clauseBuilder;

	public AbstractDbCompoundKeyTest() {

		ISqlTable<?> table = Mockito.mock(ISqlTable.class);
		this.statementBuilder = new SqlStatementBuilder(DbConnections.getServerQuirks(), table, "");
		this.clauseBuilder = new SqlClauseBuilder(statementBuilder);
	}

	public void setAlias(String alias) {

		statementBuilder.setAlias(alias);
	}

	public void build(ISqlClauseBuildable expression) {

		expression.build(clauseBuilder);
	}

	public void assertParameters(Object...expected) {

		assertEquals(Arrays.asList(expected), clauseBuilder.getParameters());
	}

	public void assertText(String expected) {

		assertEquals(expected, clauseBuilder.getText());
	}
}
