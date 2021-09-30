package com.softicar.platform.db.runtime.object;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.runtime.cache.CurrentDbTableRowCacheMap;
import java.math.BigDecimal;
import org.junit.Test;

public class DbObjectResultSetReaderTest extends AbstractDbObjectTest {

	// -------------------- foreign references -------------------- //

	// This is a very important test!
	@Test
	public void testLoadingOfSelfReference() {

		// create an object referring to itself
		Integer id = insertObjectRow("foo");
		updateObjectRowSetForeignId(id, id);

		DbTestObject object = DbTestObject.TABLE.get(id);
		assertSame(object, object.getForeign());
	}

	@Test
	public void testForeignKeyReferenceIsStub() {

		Integer objectId = insertObjectRow("foo");
		Integer foreignId = insertObjectRow("foreign");
		updateObjectRowSetForeignId(objectId, foreignId);

		DbTestObject object = DbTestObject.TABLE.get(objectId);
		assertNotNull(object.getForeign());
		assertTrue(object.getForeign().stub());
	}

	@Test
	public void testLoadingOfNullValues() {

		// insert initial object
		DbTestObject initialObject = new DbTestObject();
		initialObject.setBigDecimal(null);
		initialObject.setBoolean(null);
		initialObject.setBytes(null);
		initialObject.setDay(null);
		initialObject.setDayTime(null);
		initialObject.setDouble(null);
		initialObject.setFloat(null);
		initialObject.setForeign(null);
		initialObject.setInteger(null);
		initialObject.setLong(null);
		initialObject.setString(null);
		initialObject.save();

		// reset cache and reload object
		CurrentDbTableRowCacheMap.set(null);
		DbTestObject object = DbTestObject.TABLE.get(initialObject.getId());
		assertNotSame(initialObject, object);

		// check object
		assertNull(object.getBigDecimal());
		assertNull(object.getBoolean());
		assertNull(object.getBytes());
		assertNull(object.getDay());
		assertNull(object.getDayTime());
		assertNull(object.getDouble());
		assertNull(object.getFloat());
		assertNull(object.getForeign());
		assertNull(object.getInteger());
		assertNull(object.getLong());
		assertNull(object.getString());
	}

	@Test
	public void testLoadingOfNonNullValues() {

		// insert foreign object
		DbTestObject foreignObject = new DbTestObject();
		foreignObject.save();

		// insert initial object
		DbTestObject initialObject = new DbTestObject();
		initialObject.setBigDecimal(new BigDecimal("5.53"));
		initialObject.setBoolean(true);
		initialObject.setBytes(new byte[] { 5, 2, 3, 55 });
		initialObject.setDay(Day.fromYMD(2014, 10, 14));
		initialObject.setDayTime(DayTime.fromDayAndSeconds(Day.fromYMD(2014, 10, 14), 100));
		initialObject.setDouble(3.234);
		initialObject.setEnum(DbTestObjectFlag.BIG);
		initialObject.setFloat(3.234f);
		initialObject.setForeign(foreignObject);
		initialObject.setInteger(-3435);
		initialObject.setLong(9876543210L);
		initialObject.setString("foo");
		initialObject.save();

		// reset cache and reload object
		CurrentDbTableRowCacheMap.set(null);
		DbTestObject object = DbTestObject.TABLE.get(initialObject.getId());
		assertNotSame(initialObject, object);

		// check object
		assertEquals(0, object.getBigDecimal().compareTo(new BigDecimal("5.53")));
		assertEquals(true, object.getBoolean());
		assertArrayEquals(new byte[] { 5, 2, 3, 55 }, object.getBytes());
		assertEquals(Day.fromYMD(2014, 10, 14), object.getDay());
		assertEquals(DayTime.fromDayAndSeconds(Day.fromYMD(2014, 10, 14), 100), object.getDayTime());
		assertEquals(3.234, object.getDouble(), 0.001);
		assertEquals(DbTestObjectFlag.BIG, object.getEnum());
		assertEquals(3.234f, object.getFloat(), 0.001);
		assertEquals(foreignObject.getId(), object.getForeign().getId());
		assertEquals(-3435, object.getInteger().intValue());
		assertEquals(9876543210L, object.getLong().longValue());
		assertEquals("foo", object.getString());
	}
}
