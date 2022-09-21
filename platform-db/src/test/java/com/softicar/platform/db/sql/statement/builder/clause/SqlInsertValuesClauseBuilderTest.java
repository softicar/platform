package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;
import java.util.Arrays;
import org.junit.Test;
import org.mockito.Mockito;

public class SqlInsertValuesClauseBuilderTest extends AbstractTest {

	private static final String FOO = "foo";
	private static final String BAR = "bar";
	private final SqlInsertValuesClauseBuilder builder;

	public SqlInsertValuesClauseBuilderTest() {

		ISqlTable<?> table = Mockito.mock(ISqlTable.class);
		SqlStatementBuilder builder = new SqlStatementBuilder(DbServerType.MYSQL.getQuirks(), table, "");
		this.builder = new SqlInsertValuesClauseBuilder(builder);
	}

	@Test
	public void testValueToListWithOneValue() {

		builder.addValueToList(33);

		assertEquals(Arrays.asList(33), builder.getParameters());
		assertEquals("(?", builder.getText());
	}

	@Test
	public void testValueToListWithTwoValues() {

		builder.addValueToList(33);
		builder.addValueToList(FOO);

		assertEquals(Arrays.asList(33, FOO), builder.getParameters());
		assertEquals("(?, ?", builder.getText());
	}

	@Test
	public void testValueToListWithTwoTableRows() {

		builder.addValueToList(33);
		builder.addValueToList(FOO);
		builder.finishValueList();
		builder.addValueToList(44);
		builder.addValueToList(BAR);
		builder.finishValueList();

		assertEquals(Arrays.asList(33, FOO, 44, BAR), builder.getParameters());
		assertEquals("(?, ?), (?, ?)", builder.getText());
	}

	@Test
	public void testFinishValueListRedundantly() {

		builder.addValueToList(33);
		builder.addValueToList(FOO);
		builder.finishValueList();
		builder.finishValueList();
		builder.finishValueList();

		assertEquals(Arrays.asList(33, FOO), builder.getParameters());
		assertEquals("(?, ?)", builder.getText());

	}

	@Test
	public void testFinishValueListWithoutValues() {

		builder.finishValueList();

		assertEquals(Arrays.asList(), builder.getParameters());
		assertEquals("(DEFAULT)", builder.getText());
	}
}
