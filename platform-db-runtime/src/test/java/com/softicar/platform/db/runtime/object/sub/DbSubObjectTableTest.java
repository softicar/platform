package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class DbSubObjectTableTest extends AbstractDbTest {

	@Test
	public void testGetStubWithIntegerId() {

		DbTestSubSubObject stub = DbTestSubSubObject.TABLE.getStub(1337);

		assertNotNull(stub);
		assertNotNull(stub.getSubObject());
		assertNotNull(stub.getSubObject().getBaseObject());

		assertTrue(stub.stub());
		assertTrue(stub.getSubObject().stub());
		assertTrue(stub.getSubObject().getBaseObject().stub());
	}
}
