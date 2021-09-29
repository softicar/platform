package com.softicar.platform.db.sql.expressions.string;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression0;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import com.softicar.platform.db.sql.test.SqlTestEntity;
import org.junit.Test;

public class SqlStringExpressionTest extends AbstractSqlBuildableTest {

	@Test
	public void testLiteral() {

		build(Sql.literal("foo"));

		assertParameters("foo");
	}

	@Test
	public void testConcat() {

		build(Sql.literal("foo").concat("88"));

		assertParameters("foo", "88");
		assertText("CONCAT(?, ?)");
	}

	@Test
	public void testTrim() {

		build(Sql.literal("foo").trim());

		assertParameters("foo");
		assertText("TRIM(?)");
	}

	@Test
	public void testLength() {

		build(Sql.literal("foo").length());

		assertParameters("foo");
		assertText("LENGTH(?)");
	}

	@Test
	public void testSubstring() {

		build(Sql.literal("foo").substring(33, 42));

		assertParameters("foo", 33, 42);
		assertText("SUBSTRING(?, ?, ?)");
	}

	@Test
	public void testEqualWithLiterals() {

		ISqlBooleanExpression0 expression = Sql.literal("foo").isEqual(Sql.literal("bar"));
		build(expression);

		assertParameters("foo", "bar");
		assertText("(? = ?)");
	}

	@Test
	public void testEqualWithFieldAndLiteral() {

		ISqlBooleanExpression<SqlTestEntity> expression = SqlTestEntity.STRING.isEqual(Sql.literal("foo"));

		setAlias("t");
		build(expression);

		assertParameters("foo");
		assertText("(t.`String` = ?)");
	}

	@Test
	public void testEqualWithFields() {

		ISqlBooleanExpression2<SqlTestEntity, SqlTestEntity> expression = SqlTestEntity.STRING.isEqual(SqlTestEntity.STRING);

		setAlias("t");
		build(expression);

		assertParameters();
		assertText("(t.`String` = t.`String`)");
	}

	@Test
	public void testIsRegexpWithLiterals() {

		build(Sql.literal("foo").isRegexp("bar"));

		assertParameters("foo", "bar");
		assertText("(? REGEXP ?)");
	}

	@Test
	public void testIsNotRegexpWithLiterals() {

		build(Sql.literal("foo").isNotRegexp("bar"));

		assertParameters("foo", "bar");
		assertText("(? NOT REGEXP ?)");
	}
}
