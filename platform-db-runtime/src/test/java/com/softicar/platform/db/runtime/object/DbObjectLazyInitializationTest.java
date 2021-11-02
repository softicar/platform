package com.softicar.platform.db.runtime.object;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.sql.Sql;
import org.junit.Test;

public class DbObjectLazyInitializationTest extends AbstractDbTest {

	private final DbTestObject object;

	public DbObjectLazyInitializationTest() {

		this.object = new DbTestObject();
	}

	@Test
	public void testLazyInitializationByGettingFlags() {

		assertNotInitialized();
		object.flags();
		assertInitialized();
	}

	@Test
	public void testLazyInitializationByGettingPrimaryKey() {

		assertNotInitialized();
		object.pk();
		assertInitialized();
	}

	@Test
	public void testLazyInitializationByGettingValueOfField() {

		assertNotInitialized();
		object.getInteger();
		assertInitialized();
	}

	@Test
	public void testLazyInitializationBySave() {

		assertNotInitialized();
		object.save();
		assertInitialized();

		// check persisted values
		Tuple2<Integer, String> values = Sql//
			.from(DbTestObject.TABLE)
			.select(DbTestObject.INTEGER_FIELD)
			.select(DbTestObject.STRING_FIELD)
			.getOne();
		assertEquals(DbTestObject.DEFAULT_INTEGER_VALUE, values.get0());
		assertEquals(DbTestObject.DEFAULT_STRING_VALUE, values.get1());
	}

	private void assertNotInitialized() {

		assertNull(DbTestObject.INTEGER_FIELD.getValueDirectly(object));
		assertNull(DbTestObject.STRING_FIELD.getValueDirectly(object));
	}

	private void assertInitialized() {

		assertEquals(DbTestObject.DEFAULT_INTEGER_VALUE, DbTestObject.INTEGER_FIELD.getValueDirectly(object));
		assertEquals(DbTestObject.DEFAULT_STRING_VALUE, DbTestObject.STRING_FIELD.getValueDirectly(object));
	}
}
