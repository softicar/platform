package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.sql.Sql;
import org.junit.Test;

public class DbSubObjectLazyInitializationTest extends AbstractDbTest {

	private final DbTestSubSubObject object;

	public DbSubObjectLazyInitializationTest() {

		this.object = new DbTestSubSubObject();
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
		object.getSubSubText();
		assertInitialized();
	}

	@Test
	public void testLazyInitializationBySave() {

		assertNotInitialized();
		object.save();
		assertInitialized();

		// check persisted values
		Tuple2<DbTestSubObject, String> values = Sql//
			.from(DbTestSubSubObject.TABLE)
			.select(DbTestSubSubObject.SUB_OBJECT)
			.select(DbTestSubSubObject.SUB_SUB_TEXT)
			.getOne();
		assertEquals(object.getSubObject(), values.get0());
		assertEquals("", values.get1());
	}

	private void assertNotInitialized() {

		assertNull(DbTestSubSubObject.SUB_OBJECT.getValueDirectly(object));
		assertNull(DbTestSubSubObject.SUB_SUB_TEXT.getValueDirectly(object));
	}

	private void assertInitialized() {

		assertNotNull(DbTestSubSubObject.SUB_OBJECT.getValueDirectly(object));
		assertEquals("", DbTestSubSubObject.SUB_SUB_TEXT.getValueDirectly(object));
	}
}
