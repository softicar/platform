package com.softicar.platform.emf.management;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfManagementDataTableTest extends AbstractTest {

	@Test
	public void testGetIdentifier() {

		EmfManagementDataTable<EmfTestObject, Integer, EmfTestModuleInstance> table = new EmfManagementDataTable<>(EmfTestObject.TABLE, null, false);
		DataTableIdentifier identifier = table.getIdentifier();

		assertEquals("Test.SimpleEntity", identifier.toString());
	}
}
