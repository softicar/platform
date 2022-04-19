package com.softicar.platform.db.sql.selects;

import static com.softicar.platform.db.sql.Sql.count;
import static com.softicar.platform.db.sql.Sql.literal;
import static com.softicar.platform.db.sql.Sql.now;
import static com.softicar.platform.db.sql.Sql.select;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.test.SqlTestEntity;
import org.junit.Test;

public class SqlSelectTest extends AbstractTest {

	@Test
	public void selectNow() {

		assertEquals("SELECT NOW() []", select(now()).toString());
	}

	@Test
	public void selectCount() {

		assertEquals("SELECT COUNT(*) []", select(count()).toString());
	}

	@Test
	public void selectIntegerLiteral() {

		assertEquals("SELECT ? [1]", select(literal(1)).toString());
	}

	@Test
	public void selectStringLiteral() {

		assertEquals("SELECT ? [foo]", select(literal("foo")).toString());
	}

	@Test
	public void selectFloatLiteral() {

		assertEquals("SELECT ? [0.125]", select(literal(0.125)).toString());
	}

	@Test
	public void selectDayLiteral() {

		assertEquals("SELECT ? [1970-01-01]", select(literal(Day.get1970())).toString());
	}

	@Test
	public void testWhereWithFieldAndLiteral() {

		String statement = Sql//
			.from(SqlTestEntity.TABLE)
			.where(SqlTestEntity.STRING.isEqual("foo"))
			.select(literal(1))
			.toString();

		assertEquals("SELECT ? FROM `db`.`test` t0 WHERE (t0.`String` = ?) [1, foo]", statement);
	}

	@Test
	public void testWhereWithFieldAndFieldOnSameTable() {

		String statement = Sql//
			.from(SqlTestEntity.TABLE)
			.where()
			.t0()
			.t0(SqlTestEntity.STRING.isEqual(SqlTestEntity.STRING))
			.select(literal(1))
			.toString();

		assertEquals("SELECT ? FROM `db`.`test` t0 WHERE (t0.`String` = t0.`String`) [1]", statement);
	}

	@Test
	public void testWhereWithFieldAndFieldOnDifferentTable() {

		String statement = Sql//
			.from(SqlTestEntity.TABLE)
			.join(SqlTestEntity.TABLE)
			.where()
			.t0()
			.t1(SqlTestEntity.STRING.isEqual(SqlTestEntity.STRING))
			.select(literal(1))
			.toString();

		assertEquals("SELECT ? FROM `db`.`test` t0 JOIN `db`.`test` t1 WHERE (t0.`String` = t1.`String`) [1]", statement);
	}

	@Test
	public void testWhereWithBooleanOrExpression() {

		String statement = Sql//
			.from(SqlTestEntity.TABLE)
			.where(SqlTestEntity.STRING.isEqual("foo").or(SqlTestEntity.INTEGER.isEqual(7)))
			.select(literal(1))
			.toString();

		assertEquals("SELECT ? FROM `db`.`test` t0 WHERE ((t0.`String` = ?) OR (t0.`Integer` = ?)) [1, foo, 7]", statement);
	}

	@Test
	public void testJoins() {

		String expectedSelect = "SELECT t0.`String` FROM `db`.`test` t0 JOIN `db`.`test` t1 ON (t1.`Foreign` = t0.`id`) []";
		String generatedSelect = Sql//
			.from(SqlTestEntity.TABLE)
			.joinReverseOnTable0(SqlTestEntity.FOREIGN)
			.select()
			.t0(SqlTestEntity.STRING)
			.toString();
		assertEquals(expectedSelect, generatedSelect);
	}
}
