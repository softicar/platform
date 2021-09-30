package com.softicar.platform.db.runtime.utils;

import com.softicar.platform.db.runtime.object.AbstractDbObjectTest;
import com.softicar.platform.db.runtime.object.DbTestObject;
import java.util.Map;
import org.junit.Test;

public class DbObjectListTest extends AbstractDbObjectTest {

	@Test
	public void testToMap() {

		// insert objects
		DbTestObject foo = insertObject("foo");
		DbTestObject bar = insertObject("bar");
		DbTestObject baz = insertObject("baz");

		// create list
		DbObjectList<DbTestObject> list = new DbObjectList<>();
		list.add(foo);
		list.add(baz);
		list.add(bar);
		list.add(foo);
		list.add(baz);
		assertEquals(5, list.size());

		// convert to map
		Map<Integer, DbTestObject> map = list.toMap();
		assertEquals(3, map.size());
		assertSame(foo, map.get(foo.getId()));
		assertSame(bar, map.get(bar.getId()));
		assertSame(baz, map.get(baz.getId()));
	}

	@Test(expected = IllegalStateException.class)
	public void testToMapWithNonPeristentObject() {

		DbObjectList<DbTestObject> list = new DbObjectList<>();
		list.add(new DbTestObject());
		list.toMap();
	}
}
