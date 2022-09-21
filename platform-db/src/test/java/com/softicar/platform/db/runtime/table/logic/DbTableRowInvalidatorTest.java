package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class DbTableRowInvalidatorTest extends AbstractDbTest {

	/**
	 * This tests against a {@link NullPointerException} caused by missing
	 * initialization of the table row flags.
	 */
	@Test
	public void testWithUninitializedRow() {

		DbTestObject uninitializedObject = new DbTestObject();

		new DbTableRowInvalidator<>(uninitializedObject).invalidate();

		assertTrue(uninitializedObject.impermanent());
		assertTrue(uninitializedObject.invalidated());
	}
}
