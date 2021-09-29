package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuildable;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import org.junit.Test;

public class SqlDoubleExpressionTest extends AbstractSqlBuildableTest {

	public static final double VALUE = 1337.7;
	public static final SqlDoubleExpression0 EXPRESSION = Sql.literal(VALUE);

	@Test
	public void neg() {

		test("(-?)", EXPRESSION.neg());
	}

	@Test
	public void abs() {

		test("ABS(?)", EXPRESSION.abs());
	}

	@Test
	public void plus() {

		test("(? + ?)", EXPRESSION.plus(42.0));
		assertParameters(VALUE, 42.0);
	}

	@Test
	public void minus() {

		test("(? - ?)", EXPRESSION.minus(42.0));
		assertParameters(VALUE, 42.0);
	}

	@Test
	public void times() {

		test("(? * ?)", EXPRESSION.times(42.0));
		assertParameters(VALUE, 42.0);
	}

	@Test
	public void modulo() {

		test("(? % ?)", EXPRESSION.modulo(42.0));
		assertParameters(VALUE, 42.0);
	}

	private void test(String expectedText, ISqlClauseBuildable expression) {

		build(expression);
		assertText(expectedText);
	}
}
