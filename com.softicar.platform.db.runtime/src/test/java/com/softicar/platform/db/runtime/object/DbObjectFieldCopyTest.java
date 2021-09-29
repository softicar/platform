package com.softicar.platform.db.runtime.object;

import com.softicar.platform.common.date.Time;
import org.junit.Test;

public class DbObjectFieldCopyTest extends AbstractDbObjectTest {

	private final DbTestObject object;

	public DbObjectFieldCopyTest() {

		this.object = new DbTestObject(1234);
		this.object.setString("foo");
		this.object.setBytes(new byte[] { 1, 2, 3, 4 });
		this.object.setTime(new Time(13, 17, 31));
	}

	@Test
	public void copyDataFieldsToObject() {

		DbTestObject target = new DbTestObject(555);
		object.copyDataFieldsTo(target);

		assertEquals(555, target.getId().intValue());
		assertEquals("foo", target.getString());
		assertArrayEquals(new byte[] { 1, 2, 3, 4 }, target.getBytes());
	}
}
