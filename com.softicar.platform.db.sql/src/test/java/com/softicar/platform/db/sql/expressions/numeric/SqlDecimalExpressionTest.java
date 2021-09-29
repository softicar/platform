package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuildable;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import org.junit.Test;

public class SqlDecimalExpressionTest extends AbstractSqlBuildableTest {

	public static final double VALUE = 1337.7;
	public static final SqlDoubleExpression0 EXPRESSION = Sql.literal(VALUE);

	@Test
	public void divided() {

		test("(? / ?)", EXPRESSION.divided(42.0));
		assertParameters(VALUE, 42.0);
	}

	@Test
	public void ceil() {

		test("CEIL(?)", EXPRESSION.ceil());
	}

	@Test
	public void floor() {

		test("FLOOR(?)", EXPRESSION.floor());
	}

	@Test
	public void round() {

		test("ROUND(?)", EXPRESSION.round());
	}

	@Test
	public void roundTo() {

		test("ROUND(?, ?)", EXPRESSION.round(2));
		assertParameters(VALUE, 2);
	}

	private void test(String expectedText, ISqlClauseBuildable expression) {

		build(expression);
		assertText(expectedText);
	}
}
