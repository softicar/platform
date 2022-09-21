package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import org.junit.Test;

public class SqlIsInTest extends AbstractSqlBuildableTest {

	@Test
	public void convertsIsInEmptyListToFalse() {

		build(Sql.literal("muh").isIn());

		assertParameters();
		assertText("FALSE");
	}

	@Test
	public void convertsIsNotInEmptyListToTrue() {

		build(Sql.literal("muh").isNotIn());

		assertParameters();
		assertText("TRUE");
	}

	@Test
	public void buildsCorrectIsIn() {

		build(Sql.literal("muh").isIn("hallo", "du", "da"));

		assertParameters("muh", "hallo", "du", "da");
		assertText("(? IN (?,?,?))");
	}

	@Test
	public void buildsCorrectIsNotIn() {

		build(Sql.literal("muh").isNotIn("hallo", "du", "da"));

		assertParameters("muh", "hallo", "du", "da");
		assertText("(? NOT IN (?,?,?))");
	}
}
