package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagsAsserter;
import org.junit.Test;

public class DbObjectCreationTest extends AbstractDbObjectTest {

	@Test
	public void testFlagsAfterCreation() {

		DbTestObject object = DbTestObject.TABLE.createObject();

		new DbTableRowFlagsAsserter(object).assertOnlyImpermanent();
	}

	@Test
	public void testFieldValuesAfterCreation() {

		DbTestObject object = DbTestObject.TABLE.createObject();
		assertNull(object.getId());
		assertEquals(DbTestObject.DEFAULT_INTEGER_VALUE, object.getInteger());
		assertEquals(DbTestObject.DEFAULT_STRING_VALUE, object.getString());
	}
}
