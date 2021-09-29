package com.softicar.platform.db.runtime.table.statements;

import com.softicar.platform.db.runtime.object.DbTestObject;
import java.util.Arrays;
import org.junit.Test;

public class DbTableUpdateTest extends AbstractDbStatementTest {

	private final DbTestObject object1;
	private final DbTestObject object2;
	private final DbTestObject object3;

	public DbTableUpdateTest() {

		this.object1 = createAndSaveObject(13, "foo");
		this.object2 = createAndSaveObject(42, "foo");
		this.object3 = createAndSaveObject(99, "foo");

		executedStatements.clear();
	}

	@Test
	public void test() {

		DbTestObject.TABLE//
			.createUpdate()
			.set(DbTestObject.STRING_FIELD, "bar")
			.where(DbTestObject.INTEGER_FIELD.isIn(13, 42))
			.execute();

		assertSqlStatement(
			new StringBuilder()//
				.append("UPDATE `database`.`table` ")
				.append("SET `string` = ? ")
				.append("WHERE (`integer` IN (?,?))")
				.toString(),
			"bar",
			13,
			42);
		assertStringFields("bar", "bar", "foo");
	}

	@Test
	public void testIncrement() {

		DbTestObject.TABLE//
			.createUpdate()
			.increment(DbTestObject.INTEGER_FIELD, 7)
			.where(DbTestObject.INTEGER_FIELD.isLess(99))
			.execute();

		assertSqlStatement(
			new StringBuilder()//
				.append("UPDATE `database`.`table` ")
				.append("SET `integer` = `integer` + ? ")
				.append("WHERE (`integer` < ?)")
				.toString(),
			7,
			99);
		assertIntegerFields(20, 49, 99);
	}

	@Test
	public void testIncrementWithNegativeValue() {

		DbTestObject.TABLE//
			.createUpdate()
			.increment(DbTestObject.INTEGER_FIELD, -11)
			.where(DbTestObject.INTEGER_FIELD.isGreater(13))
			.execute();

		assertSqlStatement(
			new StringBuilder()//
				.append("UPDATE `database`.`table` ")
				.append("SET `integer` = `integer` + ? ")
				.append("WHERE (`integer` > ?)")
				.toString(),
			-11,
			13);
		assertIntegerFields(13, 31, 88);
	}

	// ------------------------------ private utilities ------------------------------ //

	private void assertStringFields(String string1, String string2, String string3) {

		reloadObjects();
		assertEquals("object1", string1, object1.getString());
		assertEquals("object2", string2, object2.getString());
		assertEquals("object3", string3, object3.getString());
	}

	private void assertIntegerFields(Integer integer1, Integer integer2, Integer integer3) {

		reloadObjects();
		assertEquals("object1", integer1, object1.getInteger());
		assertEquals("object2", integer2, object2.getInteger());
		assertEquals("object3", integer3, object3.getInteger());
	}

	private void reloadObjects() {

		DbTestObject.TABLE.reloadAll(Arrays.asList(object1, object2, object3));
	}
}
