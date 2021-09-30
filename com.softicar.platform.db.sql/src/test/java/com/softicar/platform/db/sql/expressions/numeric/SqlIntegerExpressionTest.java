package com.softicar.platform.db.sql.expressions.numeric;

import static com.softicar.platform.db.sql.Sql.literal;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import org.junit.Test;

public class SqlIntegerExpressionTest extends AbstractSqlBuildableTest {

	@Test
	public void createsCorrectLiteral() {

		build(literal(1337));

		assertParameters(1337);
	}

	@Test
	public void createsCorrectCast() {

		build(literal("1337").castToInteger());

		assertParameters("1337");
		assertText("CAST(? AS SIGNED)");
	}

	@Test
	public void createsCorrectBracketing() {

		build(literal(1337).plus(42).neg().minus(13));

		assertParameters(1337, 42, 13);
		assertText("((-(? + ?)) - ?)");
	}

	@Test
	public void equal() {

		build(literal(1337).isEqual(42).and(literal(true)));

		assertParameters(1337, 42, true);
		assertText("((? = ?) AND ?)");
	}
}
