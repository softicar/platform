package com.softicar.platform.db.sql.expressions.numeric;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.sql.ISqlStatement;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.test.SqlTestEntity;
import org.junit.Test;

public class SqlNumericExpressionTest extends AbstractTest {

	@Test
	public void testCount() {

		ISqlStatement statement = Sql//
			.from(SqlTestEntity.TABLE)
			.select(SqlTestEntity.INTEGER.count())
			.getStatement();

		assertEquals("SELECT COUNT(t0.`Integer`) FROM `db`.`test` t0", statement.getText());
	}

	@Test
	public void testSum() {

		ISqlStatement statement = Sql//
			.from(SqlTestEntity.TABLE)
			.select(SqlTestEntity.INTEGER.sum())
			.getStatement();

		assertEquals("SELECT SUM(t0.`Integer`) FROM `db`.`test` t0", statement.getText());
	}
}
