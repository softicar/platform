package com.softicar.platform.db.sql.expressions.base;

import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import org.junit.Test;

/**
 * Test cases for methods provided by {@link ISqlBasicExpression}.
 *
 * @author Oliver Richers
 */
public class SqlExpressionTest extends AbstractSqlBuildableTest {

	@Test
	public void testIsNull() {

		build(Sql.literal("foo").isNull());

		assertParameters("foo");
		assertText("(? IS NULL)");
	}

	@Test
	public void testIsNotNull() {

		build(Sql.literal("foo").isNotNull());

		assertParameters("foo");
		assertText("(? IS NOT NULL)");
	}

	@Test
	public void testIsEqual() {

		build(Sql.literal("foo").isEqual("bar"));

		assertParameters("foo", "bar");
		assertText("(? = ?)");
	}

	@Test
	public void testIsNotEqual() {

		build(Sql.literal("foo").isNotEqual("bar"));

		assertParameters("foo", "bar");
		assertText("(? != ?)");
	}

	@Test
	public void testIsLess() {

		build(Sql.literal("foo").isLess("bar"));

		assertParameters("foo", "bar");
		assertText("(? < ?)");
	}

	@Test
	public void testIsLessEqual() {

		build(Sql.literal("foo").isLessEqual("bar"));

		assertParameters("foo", "bar");
		assertText("(? <= ?)");
	}

	@Test
	public void testIsGreater() {

		build(Sql.literal("foo").isGreater("bar"));

		assertParameters("foo", "bar");
		assertText("(? > ?)");
	}

	@Test
	public void testIsGreaterEqual() {

		build(Sql.literal("foo").isGreaterEqual("bar"));

		assertParameters("foo", "bar");
		assertText("(? >= ?)");
	}

	@Test
	public void testIsIn() {

		build(Sql.literal("foo").isIn("foo", "bar"));

		assertParameters("foo", "foo", "bar");
		assertText("(? IN (?,?))");
	}

	@Test
	public void testIsInWithEmptyList() {

		build(Sql.literal("foo").isIn());

		assertParameters();
		assertText("FALSE");
	}

	@Test
	public void testIsNotIn() {

		build(Sql.literal("foo").isNotIn("foo", "bar"));

		assertParameters("foo", "foo", "bar");
		assertText("(? NOT IN (?,?))");
	}

	@Test
	public void testIsNotInWithEmptyList() {

		build(Sql.literal("foo").isNotIn());

		assertParameters();
		assertText("TRUE");
	}
}
