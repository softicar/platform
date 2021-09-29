package com.softicar.platform.db.sql.expressions.bool;

import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import org.junit.Test;

public class SqlBooleanExpressionTest extends AbstractSqlBuildableTest {

	@Test
	public void testBuildWithLiteral() {

		build(Sql.literal(true));

		assertText("?");
		assertParameters(true);
	}

	@Test
	public void testBuildWithNestedExpressions() {

		build(Sql.literal(true).and(false).or(Sql.literal(false).and(true)));

		assertText("((? AND ?) OR (? AND ?))");
		assertParameters(true, false, false, true);
	}

	@Test
	public void testColumnCount() {

		assertEquals(1, Sql.literal(true).getColumnCount());
		assertEquals(1, Sql.literal(true).and(false).getColumnCount());
	}

	@Test
	public void testGetValueType() {

		assertEquals(SqlValueTypes.BOOLEAN, Sql.literal(true).getValueType());
	}

	@Test
	public void testIsEqual() {

		build(Sql.literal(true).isEqual(false));

		assertText("(? = ?)");
		assertParameters(true, false);
	}
}
