package com.softicar.platform.common.container.data.table;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class DataTableIdentifierTest extends AbstractTest {

	@Test
	public void testIsPresent() {

		assertFalse(new DataTableIdentifier("").isPresent());
		assertFalse(new DataTableIdentifier("  ").isPresent());
		assertTrue(new DataTableIdentifier("foo").isPresent());
	}

	@Test
	public void testToString() {

		assertEquals("", new DataTableIdentifier("").toString());
		assertEquals("", new DataTableIdentifier("  ").toString());
		assertEquals("x", new DataTableIdentifier(" x ").toString());
		assertEquals("foo", new DataTableIdentifier("foo").toString());
		assertEquals("foo.bar", new DataTableIdentifier("foo.bar").toString());
	}
}
