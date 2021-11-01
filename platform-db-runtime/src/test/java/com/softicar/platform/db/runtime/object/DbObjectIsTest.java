package com.softicar.platform.db.runtime.object;

import org.junit.Test;

public class DbObjectIsTest extends AbstractDbObjectTest {

	private final DbTestObject object;

	public DbObjectIsTest() {

		this.object = new DbTestObject(1);
	}

	@Test
	public void compareToItself() {

		assertTrue(object.is(object));
	}

	@Test
	public void compareToNull() {

		assertFalse(object.is(null));
	}

	@Test
	public void compareToDifferentObjectWithSameId() {

		assertTrue(object.is(new DbTestObject(1)));
	}

	@Test
	public void compareToObjectWithDifferentId() {

		assertFalse(object.is(new DbTestObject(2)));
	}
}
