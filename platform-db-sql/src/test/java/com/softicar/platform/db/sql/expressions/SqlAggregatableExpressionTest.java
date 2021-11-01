package com.softicar.platform.db.sql.expressions;

import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import com.softicar.platform.db.sql.test.SqlTestEntity;
import org.junit.Test;

public class SqlAggregatableExpressionTest extends AbstractSqlBuildableTest {

	@Test
	public void testMaxWithInteger() {

		setAlias("t");
		build(SqlTestEntity.INTEGER.max());

		assertText("MAX(t.`Integer`)");
	}

	@Test
	public void testMaxWithString() {

		setAlias("t");
		build(SqlTestEntity.STRING.max());

		assertText("MAX(t.`String`)");
	}

	@Test
	public void testMinWithInteger() {

		setAlias("t");
		build(SqlTestEntity.INTEGER.min());

		assertText("MIN(t.`Integer`)");
	}

	@Test
	public void testMinWithString() {

		setAlias("t");
		build(SqlTestEntity.STRING.min());

		assertText("MIN(t.`String`)");
	}
}
